using AD.APP.Models;

namespace AD.APP.Services;

public class DeviceInformationService
{
    #region Events

    public event EventHandler<AccelerometerChangedEventArgs>? AccelerometerReadingChanged;
    public event EventHandler<GeolocationLocationChangedEventArgs>? GeolocationChanged;
    public event EventHandler<BatteryInfoChangedEventArgs>? BatteryInfoChanged;

    #endregion

    #region Constructor

    public DeviceInformationService() { }

    #endregion

    #region Methods

    internal DeviceInformation GetDeviceInformation()
    {
        return new DeviceInformation
        {
            Name = DeviceInfo.Current.Name,
            Manufacturer = DeviceInfo.Current.Manufacturer,
            Model = DeviceInfo.Current.Model,
            Version = DeviceInfo.Current.VersionString
        };
    }

    internal async Task StartLiveMetrics()
    {
        Accelerometer.Default.Start(SensorSpeed.Default);
        Accelerometer.Default.ReadingChanged += OnAccelerometerChanged;

        var status = await Permissions.CheckStatusAsync<Permissions.LocationWhenInUse>();
        if (!PermissionStatus.Granted.Equals(status))
        {
            status = await Permissions.RequestAsync<Permissions.LocationWhenInUse>();
        }

        if (PermissionStatus.Granted.Equals(status))
        {
            Geolocation.Default.LocationChanged += OnGeolocationChanged;
            var request = new GeolocationListeningRequest(GeolocationAccuracy.Best);
            _ = Geolocation.Default.StartListeningForegroundAsync(request);
        }

        Battery.Default.BatteryInfoChanged += OnBatteryInfoChanged;
    }

    internal void StopLiveMetrics()
    {
        Accelerometer.Default.Stop();
        Accelerometer.Default.ReadingChanged -= OnAccelerometerChanged;
        
        Geolocation.Default.StopListeningForeground();
        Geolocation.Default.LocationChanged -= OnGeolocationChanged;

        Battery.Default.BatteryInfoChanged -= OnBatteryInfoChanged;
        if (DevicePlatform.iOS.Equals(DeviceInfo.Platform))
        {
            OnBatteryInfoChanged(this, new BatteryInfoChangedEventArgs(
                Battery.Default.ChargeLevel,
                Battery.Default.State,
                Battery.Default.PowerSource));
        }
    }

    private void OnAccelerometerChanged(object? sender, AccelerometerChangedEventArgs e)
    {
        AccelerometerReadingChanged?.Invoke(sender, e);
    }

    private void OnGeolocationChanged(object? sender, GeolocationLocationChangedEventArgs e)
    {
        GeolocationChanged?.Invoke(sender, e);
    }

    private void OnBatteryInfoChanged(object? sender, BatteryInfoChangedEventArgs e)
    {
        BatteryInfoChanged?.Invoke(sender, e);
    }

    #endregion
}
