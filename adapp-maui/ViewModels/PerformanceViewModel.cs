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
                _ = ComputeUtils.SieveOfEratosthenes(ERATOSTHENES_LIMIT);
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

                memorySamples.Add(GC.GetTotalMemory(forceFullCollection: false) * BYTES_TO_MB);

                stopwatch.Stop();
                totalMilliseconds += stopwatch.ElapsedMilliseconds;
            }

            double slope = ComputeUtils.ComputeGrowthRate(memorySamples);
            (double rmse, double intercept) = ComputeUtils.ComputeVolatility(memorySamples, slope);

            return (intercept, slope, rmse, totalMilliseconds / (double)MEMORY_STRESS_CYCLES);
        });

        GrowthRate = slope;
        Volatility = rmse;
        Intercept = intercept;
        MemoryAverageElapsetTimePerCycle = memoryAverageElapsetTimePerCycle;

        memorySamples.Clear();
        survivors.Clear();
        IsMemoryRunning = false;
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
