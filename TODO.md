# Fix POST /api/turnos Endpoint

## Steps:
- [x] 1. Edit src/main/java/com/consultorio/pacientes/entities/Turno.java: Change @JoinColumn(nullable = false) to nullable = true on paciente.
- [x] 2. Run `mvn clean compile` to verify compilation. (BUILD SUCCESS)
- [ ] 3. Restart Spring Boot app (`./mvnw spring-boot:run`).
- [ ] 4. Test POST http://localhost:8080/api/turnos with given JSON.
- [ ] 5. Mark complete.


Paciente
 └── Historia Clínica
      ├── Datos clínicos
      ├── Diagnóstico principal
      └── Diagnósticos

y toda la administración de diagnósticos:
alta,edición,baja,principal

hacerla desde: Editar Historia Clínica