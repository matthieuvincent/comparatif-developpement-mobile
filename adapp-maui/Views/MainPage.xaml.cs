using AD.APP.ViewModels;
using AD.APP.Views.Base;

namespace AD.APP.Views;

public partial class MainPage : BaseTabbedPage<MainViewModel>
{
    public MainPage(
        MainViewModel viewModel,
        PerformancePage performancePage,
        QrCodePage qrCodePage,
        DevicePage devicePage) : base(viewModel)
    {
        InitializeComponent();
        viewModel.InitializePages(devicePage, qrCodePage, performancePage);

        Children.Add(devicePage);
        Children.Add(qrCodePage);
        Children.Add(performancePage);
    }
}
