namespace AD.APP.ViewModels.Base;

public abstract class BaseTabContentViewModel : BaseViewModel
{
    #region Properties

    private string _icon;
    public string Icon
    {
        get => _icon;
        set => SetProperty(ref _icon, value);
    }

    #endregion

    #region Constructor

    public BaseTabContentViewModel(string title, string icon) : base(title)
    {
        _icon = icon;
    }

    #endregion
}
