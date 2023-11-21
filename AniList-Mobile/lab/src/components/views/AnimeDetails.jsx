import React from 'react';
import { View, Text, StyleSheet, Image,ScrollView } from 'react-native';
import { WebView } from 'react-native-webview';

const AnimeDetails = ({route }) => {
    // con route.params se obtiene los parametros que le pasamos a la ruta
    // en este caso el parametro es ani
    const { ani} = route.params;
    // Usa el animeId como sea necesario
    if (ani.description !== null){
      ani.description = cleanDescription(ani.description);
      }
  return (
    <ScrollView>
      <View style={styles.container}>
            <View style={styles.banner}>
            {ani.bannerImage ? (
                <Image source={{uri: ani.bannerImage}} style={styles.bannerI}/>
                ) : (                
                <Image source={require('../../assets/images/banner_null.jpg')} style={styles.bannerI}/>
          )}
            </View>
            <View style={styles.content}>
                <View style={styles.imageContainer}>
                <Image source={{uri: ani.coverImage.large}} style={styles.image}/>
                <View style={styles.generes}>
                            <Text style={styles.title}>Genres</Text>
                            <View style={styles.genresContainer}>
                                {ani.genres.map((genero, index) => (
                                    <View key={index} style={styles.genre}>
                                        <Text style={styles.genreText}>{genero}</Text>
                                    </View>
                                ))}
                            </View>
                        </View>
                </View>
                <View style={styles.textContainer}>
              <Text style={styles.title}>{ani.title.romaji}</Text>
              <Text style={styles.description}>{ani.description}</Text>
            </View> 
            </View>
            <View style={styles.container2}>
                <Text style={styles.title}>Trailer</Text>
              <View style={styles.ContainerTrailer}>
                      <WebView
                          style={styles.video}
                          javaScriptEnabled={true}
                          domStorageEnabled={true}
                          allowsFullscreenVideo={true}
                          source={{ uri: `https://www.youtube.com/embed/${ani.trailer.id}` }}
                      />
              </View>
            </View>

        </View>
    </ScrollView>
    
  );
};

const styles = StyleSheet.create({
  scrollViewContainer: {
    flexGrow: 1,
  },
  container: {
    flex: 1,
    backgroundColor: 'rgb(11, 22, 34)',
  },
  banner:{
    height: 150,
    backgroundColor: '#fff',
  },
  bannerI:{
    width:"100%",
    height: "100%",
  },
  content: {
    flexDirection: 'row',
    backgroundColor: 'rgb(229,235,241)',
    padding: 10,
  },
  image:{
    width:200,
    height:300,
    marginRight: 10,

  },
  textContainer: {
    flex: 1,
    flexDirection: 'column',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
  },
  imageContainer:{
    width:210,
  },
  generes:{
    marginTop: 10,
    height: 89,
  },
  genresContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
},
genre: {
  backgroundColor: 'rgb(18,172,253)',
  borderRadius: 5,
  marginRight: 12,
  marginBottom: 8,
  paddingHorizontal: 10,
  paddingVertical: 5,
},
container2:{
  backgroundColor: 'rgb(229,235,241)',
  padding:10,
},
ContainerTrailer: {
  height: 300,
  paddingHorizontal:10,
},

});
function cleanDescription(description) {
    // Eliminar etiquetas HTML
    const cleanText = description.replace(/<[^>]+>/g, '');
    
    // Eliminar espacios en blanco duplicados y otros caracteres no deseados
    const finalCleanText = cleanText.replace(/\s+/g, ' ').trim();
  
    return finalCleanText;
  }
export default AnimeDetails;
