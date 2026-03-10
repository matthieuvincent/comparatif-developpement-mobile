using AD.APP.ViewModels;
using AD.APP.Views.Base;

namespace AD.APP.Views;

public partial class PerformancePage :  BaseContentPage<PerformanceViewModel>
{
	public PerformancePage(PerformanceViewModel viewModel) : base(viewModel)
    {
		InitializeComponent();
        viewModel.CollectionViewReference = new WeakReference<CollectionView>(ImagesCollectionView);
    }
}