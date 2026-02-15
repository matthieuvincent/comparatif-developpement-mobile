using AD.APP.Resources;
using AD.APP.ViewModels.Base;
using BarcodeScanning;

namespace AD.APP.ViewModels;

public class QrCodeViewModel : BaseTabContentViewModel
{
    #region Constants

    private const string ICON = "qrcode.png";

    #endregion

    #region Properties

    private bool _permissionAccepted;
    public bool PermissionAccepted
    {
        get => _permissionAccepted;
        set => SetProperty(ref _permissionAccepted, value);
    }

    #endregion

    #region Constructor

    public QrCodeViewModel() : base(Labels.qrTitle, ICON) { }

    #endregion

    #region Methods

    public async Task AskPermission()
    {
        PermissionAccepted = await Methods.AskForRequiredPermissionAsync();
    }

    #endregion
}
