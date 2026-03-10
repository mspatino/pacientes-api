Mobile App / Web
        │
        ▼
pacientes-api
        │
        ├ /api/login
        ├ /api/refresh-token
        ├ JWT validation
        └ endpoints protegidos

pacientes-api
│
├── controllers
│     ├── AuthController
│     ├── PacienteController
│     └── HistoriaClinicaController
│
├── services
│     ├── PacienteService
│     ├── HistoriaClinicaService
│     └── AuthService
│
├── repositories
│     ├── PacienteRepository
│     ├── HistoriaClinicaRepository
│     └── RefreshTokenRepository
│
├── entities
│     ├── Paciente
│     ├── HistoriaClinica
│     ├── Usuario
│     └── RefreshToken
│
└── security
      ├── SecurityConfig
      ├── JwtService
      ├── JwtAuthenticationFilter
      └── CustomUserDetailsService      

Flujo de autenticacion

CLIENTE
   │
   │ POST /api/login
   │ username + password
   ▼
AuthController
   │
   ▼
Spring Security
   │
   │ valida usuario
   ▼
AuthService
   │
   ├ genera Access Token (JWT)
   └ genera Refresh Token
   ▼
Guardar Refresh Token en DB
   ▼
Respuesta al cliente      