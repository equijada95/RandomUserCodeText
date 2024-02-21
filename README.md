## Proyecto RandomUserCodeText ##

### Arquitectura ###

Para este proyecto he utilizado una arquitectura que ideé en su momento para proyectos así que se basa un poco en la arquitectura VIPER con ViewModels para utilizar corrutinas.
Se podría haber complicado un poco añadiendo un módulo presentation, separando asi la capa app que tendría el application y el presentation donde estarían las vistas y el ViewModel, o quizá añadiendo use cases para comunicar el ViewModel con el repository, pero yo creo que para un proyecto de esta escala no era necesario.

### Tests ###

Para los tests he utilizado JUnit5, que es la librería más actual para realizar tests, y Flow Turbine para testear los estados y eventos del ViewModel.

### Capa de vista ###

Para la capa de vista he utilizado Jetpack Compose, ya que es el nuevo estándar y lo veo mejor para programar las vistas que en XML, y las navegaciones tambien he utilizado las propias de Jetpack Compose para navegar 
entre vistas composables, teniendo solo una activity como contenedor. Podría haber utilizado fragments como contenedor e ir navegando entre ellos pero las navegaciones compose me parecen una cosa curiosa y facil de utilizar,
sobre todo para un proyecto de esta escala.

### Librerias ###

En el tema librerías he utilizado Glide para mostrar imágenes desde una URL ya que ya estaba familiarizado con ella.

Para las llamadas a la API he utilizado Retrofit ya que es la que más he utilizado a lo largo de mi carrera y la veo bastante fácil de usar.

También he utilizado puntualmente la librería de Gson, ya que la necesitaba para las navegaciones compose ya que se requiere convertir los modelos en un json para pasar la informacion de una pantalla a otra.

### Inyección de dependencias ###

Para la inyección de dependencias he utilizado Hilt, ya que ya había trabajado anteriormente con ella en mis proyectos y sé gestionarlo en este tipo de proyectos.
Pero uno de los incovenientes que le veo a este inyector es que todos los módulos tienen que depender de librerias android, con otro inyector como Anvil (el cual me parece bastante interesante) podría haber dejado las dependencias Android solo para el módulo de vista. 

### Dificultades ###

La toolbar de la vista detalle, ya que nunca había hecho una vista así, pero probando varias opciones al final di con la que más se asemejaba al diseño.
