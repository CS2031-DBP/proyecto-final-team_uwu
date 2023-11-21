import React, { useEffect, useState } from 'react'
import { Image, Text, View ,StyleSheet,TouchableOpacity,Platform} from 'react-native'
import * as Font from 'expo-font';
import { useNavigation } from '@react-navigation/native';

export const Header1 = () => {
  const navigation = useNavigation();

  const [fontLoaded, setFontLoaded] = useState(false);
  const handleProfilePress = () => {
    navigation.navigate('ProfileUser');
  };
  useEffect(() => {
    async function loadFont() {
      await Font.loadAsync({
        'your-custom-font': require('../../assets/fonts/ToonyLine_PERSONAL_USE_ONLY.otf'),
      });
      setFontLoaded(true);
    }
    loadFont();
  }, []);
  return (
    <View style={[styles.container, {marginTop: Platform.OS === "android" && 30}]}>
        <View style={styles.leftContainer}>
              {fontLoaded && <Text style={styles.title}>ANI FORUM</Text>}
        </View>
        <View style={styles.rightContainer}>
        <TouchableOpacity onPress={handleProfilePress}>
          <Image source={require('../../assets/images/kaguya_logo.png')} style={styles.image} />
        </TouchableOpacity>
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  container:{
    marginHorizontal:15,
    flexDirection:"row",
    alignItems:"center"
  },
  leftContainer:{
    flex:1,
    alignItems:"flex-start"
  },
  rightContainer:{
    flex:1,
    alignItems:"flex-end"
  },
  title:{
    fontSize:40,
    fontFamily: 'your-custom-font', // Esto usará tu fuente personalizada
    color:"#fff"
  },
  image:{
    width:60,
    height:60
  }

});
