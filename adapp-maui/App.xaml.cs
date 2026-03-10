using Microsoft.Extensions.DependencyInjection;

namespace AD.APP
{
    public partial class App : Application
    {
        public App(IServiceProvider serviceProvider)
        {
            InitializeComponent();
            
            var mainPage = serviceProvider.GetRequiredService<Views.MainPage>();
            MainPage = mainPage;
        }

        protected override Window CreateWindow(IActivationState? activationState)
        {
            return new Window(MainPage!);
        }
    }
}