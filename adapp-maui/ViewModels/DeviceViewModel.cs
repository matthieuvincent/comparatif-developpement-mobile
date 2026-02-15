using AD.APP.Resources;
using AD.APP.Services;
using AD.APP.ViewModels.Base;

namespace AD.APP.ViewModels;

public partial class DeviceViewModel : BaseTabContentViewModel
{
    #region Constants

    private const string ICON = "device.png";

    #endregion

    #region Properties

    private string _deviceName;
    public string DeviceName
    {
        get => _deviceName;
        set => SetProperty(ref _deviceName, value);
    }

    private string _manufacturer;
    public string Manufacturer
    {
        get => _manufacturer;
        set => SetProperty(ref _manufacturer, value);
    }

    private string _model;
    public string Model
    {
        get => _model;
        set => SetProperty(ref _model, value);
    }

    private string _version;
    public string Version
    {
        get => _version;
        set => SetProperty(ref _version, value);
    }

    private float _accelerometerX;
    public float AccelerometerX
    {
        get => _accelerometerX;
        set => SetProperty(ref _accelerometerX, value);
    }

    private float _accelerometerY;
    public float AccelerometerY
    {
        get => _accelerometerY;
        set => SetProperty(ref _accelerometerY, value);
    }

    private float _accelerometerZ;
    public float AccelerometerZ
    {
        get => _accelerometerZ;
        set => SetProperty(ref _accelerometerZ, value);
    }

    private double _latitude;
    public double Latitude
    {
        get => _latitude;
        set => SetProperty(ref _latitude, value);
    }

    private double _longitude;
    public double Longitude
    {
        get => _longitude;
        set => SetProperty(ref _longitude, value);
    }

    private double? _altitude;
    public double? Altitude
    {
        get => _altitude;
        set => SetProperty(ref _altitude, value);
    }

    private double _batteryLevel;
    public double BatteryLevel 
    { 
        get => _batteryLevel;
        set => SetProperty(ref _batteryLevel, value);
    }

    private DeviceInformationService _deviceInformationService;

    #endregion

    #region Constructor

    public DeviceViewModel(DeviceInformationService deviceInformationService) : base(Labels.deviceTitle, ICON)
    {
        _deviceInformationService = deviceInformationService;

        _accelerometerX = 0f;
        _accelerometerY = 0f;
        _accelerometerZ = 0f;

        _latitude = double.NaN;
        _longitude = double.NaN;
        _altitude = double.NaN;

        _batteryLevel = double.NaN;

        GetDeviceInformation();
        _deviceInformationService.AccelerometerReadingChanged += OnAccelerometerReadingChanged;
        _deviceInformationService.GeolocationChanged += OnGeolocationChanged;
        _deviceInformationService.BatteryInfoChanged += OnBatteryInfoChanged;
    }

    #endregion

    #region Methods

    private void GetDeviceInformation()
    {
        var deviceInfo = _deviceInformationService.GetDeviceInformation();
        DeviceName = deviceInfo.Name;
        Manufacturer = deviceInfo.Manufacturer;
        Model = deviceInfo.Model;
        Version = deviceInfo.Version;
    }

    internal void StartLiveMetrics()
    {
        _ = _deviceInformationService.StartLiveMetrics();
    }

    internal void StopLiveMetrics()
    {
        _deviceInformationService.StopLiveMetrics();
    }

    #endregion

    #region Subscribed events

    private void OnAccelerometerReadingChanged(object? sender, AccelerometerChangedEventArgs e)
    {
        AccelerometerX = e.Reading.Acceleration.X;
        AccelerometerY = e.Reading.Acceleration.Y;
        AccelerometerZ = e.Reading.Acceleration.Z;
    }

    private void OnGeolocationChanged(object? sender, GeolocationLocationChangedEventArgs e)
    {
        Latitude = e.Location.Latitude;
        Longitude = e.Location.Longitude;
        Altitude = e.Location.Altitude;
    }

    private void OnBatteryInfoChanged(object? sender, BatteryInfoChangedEventArgs e)
    {
        BatteryLevel = e.ChargeLevel * 100;
    }

    #endregion
}
