using CommunityToolkit.Mvvm.ComponentModel;

namespace AD.APP.Views.Base;

public abstract class BaseTabbedPage<TViewModel> : TabbedPage where TViewModel : ObservableObject
{
    public BaseTabbedPage(TViewModel viewModel)
    {
        base.BindingContext = viewModel;
    }

    protected new TViewModel BindingContext => (TViewModel)base.BindingContext;
}
