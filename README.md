🛒 TiendaOnlineFirebase

Aplicación e‑commerce para Android desarrollada con Kotlin, Jetpack Compose y arquitectura MVVM.
  Usa Firebase como Backend‑as‑a‑Service para autenticación, base de datos en tiempo real y almacenamiento,
ofreciendo experiencia de compra ágil y segura desde el móvil.

Proyecto personal de portafolio → demuestra diseño de UI moderna, patrones limpios y Cloud integration.

✨ Funcionalidades principales

Módulo

Detalle

Autenticación

Registro e inicio de sesión con email / password mediante Firebase Auth (lista para Google & Facebook).

Catálogo de productos

Consulta en tiempo real desde Firebase Realtime Database / Firestore y renderizado con LazyColumn.

Carrito de compras

Añadir / quitar productos; el estado se sincroniza automáticamente por usuario.

Checkout & Órdenes

Cálculo de subtotal, descuento, impuestos y total; creación de órdenes persistentes en Firebase.

Tema claro forzado

UI consistente (modo Light) independientemente del tema del dispositivo.

🧰 Stack tecnológico

Kotlin 2.0

Jetpack Compose (Material 3) – UI declarativa, navegación con NavHost

Arquitectura MVVM – ViewModel, StateFlow / LiveData

Firebase

Auth (Email, Google, Facebook roadmap)

Realtime Database / Firestore (datos en tiempo real)



Stripe SDK – integración de pagos (en progreso)

app/
├─ components/        # Reusable Compose components
├─ model/             # Data models (ProductModel, UserModel…)
├─ pages/             # Pantallas: CartPage, CheckOutPage, etc.
├─ screen/            # Pantallas de alto nivel + navegación
├─ ui/                # Theming y estilos
└─ viewmodel/         # Lógica de presentación (AuthVM, CartVM…)









Ciro Devs

Desarrollador Android • Kotlin fanboy

📧 Email

ciro.email@example.com

🔗 LinkedIn

https://linkedin.com/in/tu‑perfil



