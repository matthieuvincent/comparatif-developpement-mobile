using AD.APP.ViewModels.Base;
using AD.APP.Views;

namespace AD.APP.ViewModels;

public class MainViewModel : BaseViewModel
{
    #region Properties

    public DevicePage? DevicePage { get; private set; }
    public QrCodePage? QrCodePage { get; private set; }
    public PerformancePage? PerformancePage { get; private set; }

    #endregion

    #region Constructor

    public MainViewModel() : base("AD.APP") { }

    #endregion

    #region Methods

    public void InitializePages(DevicePage devicePage, QrCodePage qrCodePage, PerformancePage performancePage)
    {
        DevicePage = devicePage;
        QrCodePage = qrCodePage;
        PerformancePage = performancePage;
    }

    #endregion
}

