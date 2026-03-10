using CommunityToolkit.Mvvm.ComponentModel;

namespace AD.APP.Views.Base;

public abstract class BaseContentPage<TViewModel> : ContentPage where TViewModel : ObservableObject
{
    public BaseContentPage(TViewModel viewModel)
    {
        base.BindingContext = viewModel;
    }

    protected new TViewModel BindingContext => (TViewModel)base.BindingContext;
}
