import {useEffect, useState} from "react";
import * as Location from 'expo-location';

export const useLocation = (accuracy = Location.Accuracy.Highest) => {
    const [latitude, setLatitude] = useState(0);
    const [longitude, setLongitude] = useState(0);
    const [altitude, setAltitude] = useState(null);

    useEffect(() => {
        let subscription: { remove: any; };

        const startTracking = async () => {
          const { status } = await Location.requestForegroundPermissionsAsync();

          if (status !== 'granted') return;

          subscription = await Location.watchPositionAsync(
            {
              accuracy: accuracy,
              timeInterval: 5000,
              distanceInterval: 10,
            },
            (newLocation) => {
              const { latitude, longitude, altitude } = newLocation.coords;
              setLatitude(latitude);
              setLongitude(longitude);
              setAltitude(altitude);
            }
          );
        };

        startTracking();

        return () => {
          if (subscription) subscription.remove();
        };
      }, [accuracy]);

    return { latitude, longitude, altitude };
  };