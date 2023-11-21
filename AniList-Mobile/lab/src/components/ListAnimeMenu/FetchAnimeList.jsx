import { useNavigation } from '@react-navigation/native';
import React from 'react'
import { 
  View,
  Text, 
  StyleSheet,
  Image,
  Dimensions,
  TouchableOpacity,
  FlatList,
  Animated,
} from 'react-native';
const width = Dimensions.get('window').width;
const height = Dimensions.get('window').height;
const ANCHO_CONTENEDOR = width*0.3;
export const FetchAnimeList = ({animeData,title}) => {
    const navigation = useNavigation();
    const scrollX = React.useRef(new Animated.Value(0)).current;
    const renderItem = ({ item, index }) => {
        const handlePress = (anime) => {
            // anime es el objeto que se pasa como parámetro
            // a la pantalla AnimeDetails
            // ani es el nombre del parámetro
            navigation.navigate('AnimeDetails', { ani: anime }); // Redirige a la pantalla de AnimeDetails
          };
      
        return (
        <View style={{width:ANCHO_CONTENEDOR} }>
            <Animated.View style={{
            marginHorizontal: 10,
            paddingHorizontal:10,
            justifyContent: 'center',
            alignItems: 'center',
          }}>
          <TouchableOpacity onPress={() => handlePress(item)} style={styles.cover2}>
            <Image style={styles.animeImage2} source={{ uri: item.coverImage.large }} />
          <Text numberOfLines={2}  style={styles.animeTitle2}>{item.title.romaji}</Text>

          </TouchableOpacity>

        </Animated.View>
        </View> 
        );
      };

    return (
      <View style={styles.AnimeActivity}>
      <Text style={styles.animetitle}>{title}</Text>
      <View style={styles.container}>
        <Animated.FlatList
        onScroll={Animated.event(
          [{ nativeEvent: { contentOffset: { x: scrollX
           } } }],
            { useNativeDriver: true })}
          data={animeData}
          horizontal
          renderItem={renderItem}
          keyExtractor={(item) => item.id}
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={styles.carousel}
          decelerationRate={0}
          snapToInterval={ANCHO_CONTENEDOR}
          scrollEventThrottle={16}
        />
      </View>
    </View>
      );
    };
    
    const styles = StyleSheet.create({
            AnimeActivity:{
              paddingHorizontal: 10,
              height: ANCHO_CONTENEDOR + 100,
            },
            container:{
              alignItems:"center"
            },
          animetitle: {
            fontSize:30,
            color:"rgb(173,192,210)",
            fontWeight:"bold",
            paddingBottom:10,
            alignItems:"flex-end",
            alignContent:"flex-end",
          },
          animeTitle2: {
            fontSize: 11,
            fontWeight: 'bold',
            color: "#fff",
            paddingTop:5,
            marginBottom: 5,
            alignItems: 'center',
            justifyContent: "center",
            maxWidth:120,
            width:"100%"
          },
          cover2: {
            height: ANCHO_CONTENEDOR + 50,
            justifyContent: 'center',
            alignItems: 'center',
          },
          animeImage2: {
            width: 120, // Ancho de la imagen
            height: ANCHO_CONTENEDOR, // Alto de la imagen
            resizeMode: 'cover',
            borderRadius: 10,
            margin:0,
          },
    })
  
    
