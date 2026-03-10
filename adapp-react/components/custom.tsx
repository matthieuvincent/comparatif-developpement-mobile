import React from 'react';
import {View, Text, StyleSheet, Image, StyleProp, TextStyle} from 'react-native';
import LinearGradient from 'react-native-linear-gradient';
import {ImageListProps} from "@/models/ui-object";
import {AccessibleText} from "@/constants/labels";

interface AccTextProps {
  accessibleText: AccessibleText;
  style?: StyleProp<TextStyle>;
}

export const AccText = ({ accessibleText, style }: AccTextProps) => {
  return (
    <Text
      accessible={true}
      accessibilityLabel={accessibleText.accessibility}
      style={style}
    >
      { accessibleText.visual }
    </Text>
  )
}

export const PageTitle = ({ accessibleText }: AccTextProps) => (
  <AccText
    accessibleText={accessibleText}
    style={styles.pageTitle} />
);

export const DataCard = ({ accessibleText, children }: { accessibleText: AccessibleText, children: React.ReactNode }) => (
  <View style={styles.card}>
    <View style={styles.cardContent}>
      <AccText
        accessibleText={accessibleText}
        style={styles.cardTitle} />
      {children}
    </View>
  </View>
);

export const ImageList = ({ imageSource }: ImageListProps) => {
  return (
    <>
      <Image
        source={imageSource}
        style={styles.image}
        resizeMode="cover"
      />
      <LinearGradient
        start={{ x: 0, y: 0 }}
        end={{ x: 1, y: 0 }}
        colors={['green', 'yellow']}
        style={styles.gradient}
      />
    </>
  );
};

const styles = StyleSheet.create({
  pageTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#000',
  },
  card: {
    width: '100%',
    backgroundColor: '#FFFFFF',
    borderRadius: 10,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.2,
    shadowRadius: 1.41,
  },
  cardContent: {
    padding: 15,
    gap: 10,
  },
  cardTitle: {
    fontSize: 15,
    fontWeight: 'bold',
    color: '#000',
  },
  image: {
    height: 190,
    width: '100%',
  },
  gradient: {
    height: 150,
    width: '100%',
    opacity: 0.8,
    borderWidth: 1,
    borderColor: 'lightgray'
  },
});