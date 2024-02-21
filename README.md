## Proyecto RandomUserCodeText ##

### Arquitectura ###

Para este proyecto he utilizado una arquitectura que ideé en su momento para proyectos que se basa un poco en la arquitectura VIPER con ViewModels para utilizar corrutinas.
Se podría haber complicado un poco añadiendo un módulo presentation o quizá añadiendo use cases para comunicar el ViewModel con el repository, pero yo creo que para un proyecto de esta escala no era necesario.

### Tests ###

Para los tests he utilizado JUnit5, que es la librería más actual para realizar tests, y Flow Turbine para testear los estados y eventos del ViewModel.

### Capa de vista ###

Para la capa de vista he utilizado Jetpack Compose, ya que es el nuevo estándar y lo veo mejor para programar las vistas que en XML, y las navegaciones tambien he utilizado las propias de Jetpack Compose para navegar 
entre vistas composables, teniendo solo una activity como contenedor. Podría haber utilizado fragments como contenedor e ir navegando entre ellos pero las navegaciones compose me parecen una cosa curiosa y facil de utilizar,
sobre todo para un proyecto de esta escala.
