using CommunityToolkit.Mvvm.ComponentModel;

namespace AD.APP.ViewModels.Base;

public abstract class BaseViewModel : ObservableObject
{
    #region Properties

    private string _title;
    public string Title
    {
        get => _title;
        set => SetProperty(ref _title, value);
    }

    #endregion

    #region Constructor

    public BaseViewModel(string title)
    {
        _title = title;
    }

    #endregion
}
