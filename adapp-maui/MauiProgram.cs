using AD.APP.Services;
using AD.APP.ViewModels;
using AD.APP.Views;
using BarcodeScanning;
using CommunityToolkit.Maui;
using Microsoft.Extensions.Logging;

namespace AD.APP
{
    public static class MauiProgram
    {
        public static MauiApp CreateMauiApp()
        {
            var builder = MauiApp.CreateBuilder();
            builder
                .UseMauiApp<App>()
                .UseMauiCommunityToolkit()
                .UseBarcodeScanning()
                .ConfigureFonts(fonts =>
                {
                    fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                    fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
                });

#if DEBUG
    		builder.Logging.AddDebug();
#endif

            RegisterServices(builder.Services);
            RegisterViews(builder.Services);
            RegisterViewModels(builder.Services);

            return builder.Build();
        }

        private static void RegisterServices(IServiceCollection services)
        {
            services.AddTransient<DeviceInformationService>();
        }

        private static void RegisterViews(IServiceCollection services)
        {
            services.AddTransient<PerformancePage>();
            services.AddTransient<DevicePage>();
            services.AddTransient<QrCodePage>();
            services.AddSingleton<MainPage>();
        }

        private static void RegisterViewModels(IServiceCollection services)
        {
            services.AddTransient<PerformanceViewModel>();
            services.AddTransient<DeviceViewModel>();
            services.AddTransient<QrCodeViewModel>();
            services.AddSingleton<MainViewModel>();
        }
    }
}
