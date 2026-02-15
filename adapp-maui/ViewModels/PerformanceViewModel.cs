using AD.APP.Models;
using AD.APP.Resources;
using AD.APP.Utils;
using AD.APP.ViewModels.Base;
using System.Diagnostics;
using System.Windows.Input;

namespace AD.APP.ViewModels;

public class PerformanceViewModel : BaseTabContentViewModel
{
    #region Constants

    private const string ICON = "speed.png";
    private const double BYTES_TO_MB = 1.0 / (1024 * 1024);

    private const int ERATOSTHENES_LIMIT = 10_000_000;
    private const int ERATOSTHENES_CYCLES = 10;

    private const int MEMORY_STRESS_OBJECT_COUNT = 1_000_000;
    private const int MEMORY_STRESS_PAYLOAD_SIZE = 256;
    private const int MEMORY_STRESS_SURVIVAL_RATE_PERCENT = 10;
    private const int MEMORY_STRESS_CYCLES = 10;

    private const int UI_ITEM_COUNT = 10_000;

    private bool _isEratosthenesRunning;
    public bool IsEratosthenesRunning
    {
        get => _isEratosthenesRunning;
        set => SetProperty(ref _isEratosthenesRunning, value);
    }

    private double _cpuAverageElapsetTimePerCycle;
    public double CpuAverageElapsedTimePerCycle
    {
        get => _cpuAverageElapsetTimePerCycle;
        set => SetProperty(ref _cpuAverageElapsetTimePerCycle, value);
    }

    private bool _isMemoryRunning;
    public bool IsMemoryRunning
    {
        get => _isMemoryRunning;
        set => SetProperty(ref _isMemoryRunning, value);
    }

    private double _growthRate;
    public double GrowthRate
    {
        get => _growthRate;
        set => SetProperty(ref _growthRate, value);
    }

    private double _volatility;
    public double Volatility
    {
        get => _volatility;
        set => SetProperty(ref _volatility, value);
    }

    private double _intercept;
    public double Intercept
    {
        get => _intercept;
        set => SetProperty(ref _intercept, value);
    }

    private double _memoryAverageElapsetTimePerCycle;
    public double MemoryAverageElapsetTimePerCycle
    {
        get => _memoryAverageElapsetTimePerCycle;
        set => SetProperty(ref _memoryAverageElapsetTimePerCycle, value);
    }

    private IEnumerable<UIImage> _items;
    public IEnumerable<UIImage> Items
    {
        get => _items;
        set => SetProperty(ref _items, value);
    }

    public WeakReference<CollectionView> CollectionViewReference { get; set; }

    public ICommand RunSieveOfEratosthenesCommand { get; set; }
    public ICommand RunMemoryStressTestCommand { get; set; }

    #endregion

    #region Constructor

    public PerformanceViewModel() : base(Labels.performanceTitle, ICON)
    {
        _isEratosthenesRunning = false;
        _isMemoryRunning = false;

        _cpuAverageElapsetTimePerCycle = double.NaN;
        _growthRate = double.NaN;
        _volatility = double.NaN;
        _intercept = double.NaN;
        _memoryAverageElapsetTimePerCycle = double.NaN;

        _items = CreateUIImageList();

        RunSieveOfEratosthenesCommand = new Command(RunSieveOfEratosthenes);
        RunMemoryStressTestCommand = new Command(RunMemoryStressTest);
    }

    #endregion

    #region Methods

    private async void RunSieveOfEratosthenes(object obj)
    {
        IsEratosthenesRunning = true;
        long totalMilliseconds = 0;

        for (int i = 0; i < ERATOSTHENES_CYCLES; i++)
        {
            var elapsedMs = await Task.Run(() =>
            {
                var sw = Stopwatch.StartNew();
                var primes = SieveOfEratosthenes();
                sw.Stop();
                return sw.ElapsedMilliseconds;
            });

            totalMilliseconds += elapsedMs;
        }

        CpuAverageElapsedTimePerCycle = totalMilliseconds / (double)ERATOSTHENES_CYCLES;
        IsEratosthenesRunning = false;
    }

    private async void RunMemoryStressTest(object obj)
    {
        IsMemoryRunning = true;

        var survivors = new List<MemoryObject>();
        var memorySamples = new List<double>(capacity: MEMORY_STRESS_CYCLES);
        // Sauvegarde de la mémoire de base avant le stress test
        double baseMemory = GC.GetTotalMemory(forceFullCollection: false) * BYTES_TO_MB;
        long totalMilliseconds = 0;

        var (intercept, slope, rmse, memoryAverageElapsetTimePerCycle) = await Task.Run(() =>
        {
            for (int cycle = 1; cycle <= MEMORY_STRESS_CYCLES; cycle++)
            {
                Stopwatch stopwatch = Stopwatch.StartNew();
                for (int i = 0; i < MEMORY_STRESS_OBJECT_COUNT; i++)
                {
                    var memoryObject = new MemoryObject(MEMORY_STRESS_PAYLOAD_SIZE);

                    if (i % (100 / MEMORY_STRESS_SURVIVAL_RATE_PERCENT) == 0)
                    {
                        survivors.Add(memoryObject);
                    }
                }

                // Récupération de la mémoire en fin de cycle
                memorySamples.Add(GC.GetTotalMemory(forceFullCollection: false) * BYTES_TO_MB);

                stopwatch.Stop();
                totalMilliseconds += stopwatch.ElapsedMilliseconds;
            }

            double slope = ComputeUtils.ComputeGrowthRate(memorySamples);
            //L’intercept modélise l’empreinte mémoire structurelle du runtime, tandis que le RMSE quantifie l’instabilité induite par le Garbage Collector autour de cette base idéale.
            /*
             * Supposons qu'on ai :
             * cycle 1 -> 48
             * cycle 2 -> 56
             * cycle 3 -> 65
             * ...
             * cycle 10 -> 126
             * 
             * La régression donne :
             *      y^=8.7x+39\hat{y} = 8.7x + 39y^​=8.7x+39
             *      pente = 8.7 MB / cycle
             *      intercept = 39 MB
             *      
             *      ➡️ Cela signifie : Le framework consomme environ 39 MB avant toute création significative d’objets utilisateurs.
             */
            (double rmse, double intercept) = ComputeUtils.ComputeVolatility(memorySamples, slope);

            /*
                Intercept → empreinte fixe (coût de plateforme)
                Growth Rate → coût marginal par cycle
                RMSE → stabilité du GC
                Residual Ratio → coût réel par objet conservé
             */
            return (intercept, slope, rmse, totalMilliseconds / (double)MEMORY_STRESS_CYCLES);
        });

        GrowthRate = slope;
        Volatility = rmse;
        Intercept = intercept;
        MemoryAverageElapsetTimePerCycle = memoryAverageElapsetTimePerCycle;

        /*
Growth rate : 34,3924 MB / cycle
Volatility (RMSE) : 2,1356 MB
Intercept : 9,8994 MB
Residual Ratio : 358,8240 bytes / object

        Interpretation : 
            Growth rate : a chaque cycle, la memoire augmente de 34.4MB en moyenne

            Inercept : C'est l'empreinte structurelle du runtime MAUI avant toute accumulation liée aux cycles.

            Volatility : Variation moyenne autour de la croissance idéale. A comparer au Growth Rate (Volatility / GrowthRate) = 0.062. 6% c'est faible, cela indique une gestion très stable et prévisible de la mémoire par le GC.

            Residual ratio : Comme on fait des objets de 256 bytes, on s'attend a un ratio proche de 256 bytes / object. Ici on a 358.8 bytes / object, donc environ +100bytes d'overhead (header d'objet, alignementm références, structure du GC). C'est une mesure correcte qui ne montre pas de fuite de mémoire.
         */

        memorySamples.Clear();
        memorySamples = null;
        survivors.Clear();
        survivors = null;
        IsMemoryRunning = false;
    }

    private List<int> SieveOfEratosthenes()
    {
        int n = ERATOSTHENES_LIMIT;
        if (n < 2)
            return new List<int>();

        var isPrime = new bool[n + 1];
        Array.Fill(isPrime, true);

        isPrime[0] = false;
        isPrime[1] = false;

        int limit = (int)Math.Sqrt(n);

        for (int i = 2; i <= limit; i++)
        {
            if (!isPrime[i])
                continue;

            for (int j = i * i; j <= n; j += i)
                isPrime[j] = false;
        }

        int approxCount = (int)(n / Math.Log(n));
        var primes = new List<int>(approxCount);

        for (int i = 2; i <= n; i++)
        {
            if (isPrime[i])
                primes.Add(i);
        }

        return primes;
    }

    private List<UIImage> CreateUIImageList()
    {
        var items = new List<UIImage>(UI_ITEM_COUNT);

        for (int i = 0; i < UI_ITEM_COUNT; i++)
        {
            int imageIndex = (i % 4) + 1;
            items.Add(new UIImage { Image = $"image{imageIndex}.jpg" });
        }

        return items;
    }

    #endregion
}
