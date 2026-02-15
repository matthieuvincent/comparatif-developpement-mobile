using AD.APP.Resources;
using AD.APP.ViewModels;
using AD.APP.Views.Base;

namespace AD.APP.Views;

public partial class QrCodePage : BaseContentPage<QrCodeViewModel>
{
    #region Constructor

    public QrCodePage(QrCodeViewModel viewModel) : base(viewModel)
    {
		InitializeComponent();
    }

    #endregion

    #region Methods

    protected override void OnAppearing()
    {
        base.OnAppearing();
        BindingContext.AskPermission().ContinueWith(_ =>
        {
            MainThread.BeginInvokeOnMainThread(() =>
            {
                this.Camera.CameraEnabled = BindingContext.PermissionAccepted;
            });
        });
    }

    protected override void OnDisappearing() 
    {         
        base.OnDisappearing();
        this.Camera.CameraEnabled = false;
    }

    private void CameraView_OnDetectionFinished(object sender, BarcodeScanning.OnDetectionFinishedEventArg e)
    {
        if (e.BarcodeResults.Count > 0)
        {
            this.Camera.PauseScanning = true;

            MainThread.BeginInvokeOnMainThread(async () =>
            {
                await this.DisplayAlertAsync(Labels.qrAlertTitle, string.Format(Labels.qrAlertContent, e.BarcodeResults.First().RawValue), Labels.qrAlertDismiss);
                this.Camera.PauseScanning = false;
            });
        }
    }

    #endregion
}