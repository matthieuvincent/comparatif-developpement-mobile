using AD.APP.ViewModels;
using AD.APP.Views.Base;

namespace AD.APP.Views;

public partial class DevicePage : BaseContentPage<DeviceViewModel>
{
    public DevicePage(DeviceViewModel viewModel) : base(viewModel)
    {
        InitializeComponent();
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();
        this.BindingContext.StartLiveMetrics();
    }

    protected override void OnDisappearing() {
        base.OnDisappearing();
        this.BindingContext.StopLiveMetrics();
    }
}
