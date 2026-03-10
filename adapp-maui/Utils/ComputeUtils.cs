namespace AD.APP.Utils;

public static class ComputeUtils
{
    public static int SieveOfEratosthenes(int limit)
    {
        if (limit < 2)
            return 0;

        var isPrime = new bool[limit + 1];
        Array.Fill(isPrime, true);

        isPrime[0] = false;
        isPrime[1] = false;

        int sqrtLimit = (int)Math.Sqrt(limit);

        for (int i = 2; i <= sqrtLimit; i++)
        {
            if (!isPrime[i])
                continue;

            for (int j = i * i; j <= limit; j += i)
                isPrime[j] = false;
        }

        return isPrime.Count(true);
    }
    
    public static double ComputeGrowthRate(IReadOnlyList<double> samples)
    {
        int n = samples.Count;
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++)
        {
            double x = i + 1;
            double y = samples[i];
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        double numerator = n * sumXY - sumX * sumY;
        double denominator = n * sumX2 - sumX * sumX;
        return denominator != 0 ? (numerator / denominator) : 0.0;
    }

    public static (double rmse, double intercept) ComputeVolatility(IReadOnlyList<double> samples, double slope)
    {
        int n = samples.Count;
        double sumX = n * (n + 1) / 2.0;
        double sumY = samples.Sum();
        double intercept = (sumY - slope * sumX) / n;

        double sumSqErr = 0.0;
        for (int i = 0; i < n; i++)
        {
            double x = i + 1;
            double yHat = slope * x + intercept;
            double err = samples[i] - yHat;
            sumSqErr += err * err;
        }

        double rmse = Math.Sqrt(sumSqErr / n);
        return (rmse, intercept);
    }
}
