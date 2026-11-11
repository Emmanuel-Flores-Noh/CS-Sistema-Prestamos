# Registro de Pruebas de Caja Negra (Anexo A)

Este documento contiene el registro de ejecución de las pruebas de caja negra definidas en el Anexo A del archivo ADA 4.

---

# 1. Antes de Refactorización

## Caso general, opciones inválidas y opción 0

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-G-01 | Ninguna | 9, abc, (vacío), luego 0 | "Opción no válida." en cada entrada inválida y vuelve a pedir la opción | Pasa | 10/11/2026 | `Opción no válida.` (×3 tras 9, abc y vacío); `¡Hasta luego!` |
| CP-G-02 | Ninguna | Fin de entrada (cerrar stdin) | "Entrada finalizada." y termina sin errores | Pasa | 10/11/2026 | `Entrada finalizada.` (código de salida 0) |
| CP-G-03 | Ninguna | 2, Taladro, 0; reiniciar; 1 | Taladro aparece en el listado tras reiniciar | Pasa | 10/11/2026 | Run1: `Herramienta 'Taladro' añadida con ID 1.`; Run2: `1 Taladro Disponible` (CSV `1,Taladro,false`) |
| CP-00-01 | Ninguna | 0 | "¡Hasta luego!" y el programa termina | Pasa | 10/11/2026 | `¡Hasta luego!` |

## Opción 1: Ver herramientas y disponibilidad

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-01-01 | Sin herramientas | 1 | "No hay herramientas registradas." | Pasa | 10/11/2026 | `No hay herramientas registradas.` |
| CP-01-02 | Taladro activa; Escalera dada de baja | 1 | Taladro: Disponible. Escalera: Dada de baja | Pasa | 10/11/2026 | `1 Taladro Disponible` / `2 Escalera Dada de baja` |
| CP-01-03 | Taladro prestada a Ana hasta 2026-11-12 | 1 | Taladro: "Prestada a Ana (hasta 2026-11-12)" | Pasa | 10/11/2026 | `1 Taladro Prestada a Ana (hasta 2026-11-12)` |

## Opción 2: Añadir herramienta

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-02-01 | Ninguna | 2, Taladro | "añadida con ID 1"; se guarda 1, Taladro,false | Pasa | 10/11/2026 | `Herramienta 'Taladro' añadida con ID 1.` (CSV `1,Taladro,false`) |
| CP-02-02 | Ninguna | 2, (vacío), Escalera | "El campo no puede estar vacío."; después se añade con ID 1 | Pasa | 10/11/2026 | `El campo no puede estar vacío.` luego `Herramienta 'Escalera' añadida con ID 1.` |
| CP-02-03 | Ninguna | 2, (solo espacios), Escalera | Igual que CP-02-02 | Pasa | 10/11/2026 | `El campo no puede estar vacío.` luego `Herramienta 'Escalera' añadida con ID 1.` |
| CP-02-04 | Dos herramientas existentes | 2, Sierra | Se asigna ID 3 | Pasa | 10/11/2026 | `Herramienta 'Sierra' añadida con ID 3.` |
| CP-02-05 | Ninguna | 2, " Escalera " | Se muestra "Escalera" sin espacios sobrantes | Pasa | 10/11/2026 | `Herramienta 'Escalera' añadida con ID 1.` |

## Opción 3: Prestar herramienta

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-03-01 | Sin herramientas disponibles | 3 | "No hay herramientas disponibles para prestar." | Pasa | 10/11/2026 | `No hay herramientas disponibles para prestar.` |
| CP-03-02 | Taladro disponible | 3, 1, Ana, (Enter), (Enter) | Inicio hoy y límite hoy + 7 días | Pasa | 10/11/2026 | `Taladro prestada a Ana hasta 2026-11-17` (CSV `1,1,Ana,2026-11-10,2026-11-17`) |
| CP-03-03 | Taladro disponible | 3, 1, Ana, 2026-11-01, 2026-11-08 | Se guardan esas fechas exactas | Pasa | 10/11/2026 | `Taladro prestada a Ana hasta 2026-11-08` (CSV `1,1,Ana,2026-11-01,2026-11-08`) |
| CP-03-04 | Taladro prestada | 3 | Taladro no aparece; si se introduce su ID: "Opción no válida." | Pasa | 10/11/2026 | `Opción no válida.` (al introducir ID 1 de Taladro prestada) |
| CP-03-05 | Taladro disponible | 3, 1, (vacío) | "El campo no puede estar vacío." | Pasa | 10/11/2026 | `El campo no puede estar vacío.` |
| CP-03-06 | Taladro disponible | 3, 1, Ana, 01/11/2026 | "Formato inválido. Usa AAAA-MM-DD." | Pasa | 10/11/2026 | `Formato inválido. Usa AAAA-MM-DD.` |
| CP-03-07 | Taladro disponible | 3, 1, Ana, 2026-11-05, 2026-11-01 | "La fecha límite no puede ser anterior al préstamo."; no se guarda | Pasa | 10/11/2026 | `  Error: La fecha límite no puede ser anterior al préstamo` (sin préstamo guardado) |
| CP-03-08 | Taladro disponible | 3, 1, Ana, 2026-11-05, 2026-11-05 | Se acepta; límite igual al inicio | Pasa | 10/11/2026 | `Taladro prestada a Ana hasta 2026-11-05` |

## Opción 4: Registrar devolución

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-04-01 | Sin préstamos activos | 4 | "No hay préstamos pendientes de devolución." | Pasa | 10/11/2026 | `No hay préstamos pendientes de devolución.` |
| CP-04-02 | Límite 2026-11-08 | 4, 1, 2026-11-07 | "Devuelta a tiempo, sin penalización." | Pasa | 10/11/2026 | `Devuelta a tiempo, sin penalización.` |
| CP-04-03 | Límite 2026-11-08 | 4, 1, 2026-11-11 | "Retraso de 3 día(s). Penalización: 6.00 €" | Pasa | 10/11/2026 | `Retraso de 3 día(s). Penalización: 6.00 €` |
| CP-04-04 | Límite 2026-11-08 | 4, 1, 2026-11-08 | Sin retraso: "Devuelta a tiempo, sin penalización." | Pasa | 10/11/2026 | `Devuelta a tiempo, sin penalización.` |
| CP-04-05 | Inicio 2026-11-05 | 4, 1, 2026-11-01 | "La devolución no puede ser anterior al préstamo."; sigue activo | Pasa | 10/11/2026 | `  Error: La devolución no puede ser anterior al préstamo`; préstamo sigue activo |
| CP-04-06 | Préstamo activo con ID 1 | 4, 5 | "Opción no válida." | Pasa | 10/11/2026 | `Opción no válida.` |
| CP-04-07 | Préstamo activo | 4, 1, (Enter) | Fecha de devolución = hoy; retraso calculado según hoy | Pasa | 10/11/2026 | `Retraso de 2 día(s). Penalización: 4.00 €` (devolución = hoy 2026-11-10) |
| CP-04-08 | Préstamo ya devuelto | 4 | El préstamo devuelto no aparece en la lista | Pasa | 10/11/2026 | `No hay préstamos pendientes de devolución.` |

## Opción 5: Ver préstamos activos

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-05-01 | Sin préstamos activos | 5 | "No hay préstamos activos." | Pasa | 10/11/2026 | `No hay préstamos activos.` |
| CP-05-02 | Límite 2026-12-01 | 5 | "Al día, vence 2026-12-01" | Pasa | 10/11/2026 | `Al día, vence 2026-12-01` |
| CP-05-03 | Límite 2026-10-30; hoy 2026-11-05 | 5 | "ATRASADO 6 día(s), penalización estimada 12.00 €" | Pasa | 10/11/2026 | `ATRASADO 11 día(s), penalización estimada 22.00 €` (ejecutado 2026-11-10) |
| CP-05-04 | Préstamo devuelto | 5 | No aparece en la lista | Pasa | 10/11/2026 | Listado activo muestra `Martillo - Luis`; Taladro (devuelto) ausente |

## Opción 6: Ver historial y penalizaciones

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-06-01 | Sin devoluciones | 6 | "No hay devoluciones registradas." | Pasa | 10/11/2026 | `No hay devoluciones registradas.` |
| CP-06-02 | Dos devoluciones (3 días y 0 días; la primera 6.00 €) | 6 | Dos líneas y "Total de penalizaciones cobradas: 6.00 €" | Pasa | 10/11/2026 | Dos líneas + `Total de penalizaciones cobradas: 6.00 €` |
| CP-06-03 | Un préstamo activo y uno devuelto | 6 | Solo aparece el devuelto | Pasa | 10/11/2026 | `Historial de devoluciones:` muestra solo Taladro (devuelto) |

## Opción 7: Dar de baja una herramienta

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-07-01 | Todas las herramientas dadas de baja | 7 | "No hay herramientas que dar de baja." | Pasa | 10/11/2026 | `No hay herramientas que dar de baja.` |
| CP-07-02 | Escalera disponible | 7, 2 | "dada de baja. Su historial se conserva."; se guarda true; deja de aparecer en la opción 3 | Pasa | 10/11/2026 | `Escalera dada de baja. Su historial se conserva.` (CSV `2,Escalera,true`; ausente en opción 3) |
| CP-07-03 | Taladro prestada | 7, 1 | "No se puede dar de baja: la herramienta está prestada."; sin cambios | Pasa | 10/11/2026 | `  Error: No se puede dar de baja: la herramienta está prestada` (CSV `1,Taladro,false`) |
| CP-07-04 | Escalera ya dada de baja | 7 | No aparece; si se introduce su ID: "Opción no válida." | Pasa | 10/11/2026 | `Opción no válida.` (al introducir ID 2 de Escalera dada de baja) |
| CP-07-05 | Herramienta con préstamos antiguos | 7, ID de esa herramienta | Los préstamos siguen en el historial | Pasa | 10/11/2026 | `Historial de devoluciones:` sigue mostrando el préstamo tras la baja |

## Opción 8: Reactivar herramienta

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-08-01 | Ninguna dada de baja | 8 | "No hay herramientas dadas de baja." | Pasa | 10/11/2026 | `No hay herramientas dadas de baja.` |
| CP-08-02 | Escalera dada de baja | 8, 2 | "reactivada y disponible para préstamo."; se guarda false | Pasa | 10/11/2026 | `Escalera reactivada y disponible para préstamo.` (CSV `2,Escalera,false`) |
| CP-08-03 | Taladro activa | 8 | No aparece; si se introduce su ID: "Opción no válida." | Pasa | 10/11/2026 | `Opción no válida.` (al introducir ID 1 de Taladro activa) |
| CP-08-04 | Herramienta con historial | 8, ID de esa herramienta | El historial de la opción 6 no cambia | Pasa | 10/11/2026 | `Historial de devoluciones:` sin cambios tras reactivar |

---

# 2. Después de Refactorización

## Caso general, opciones inválidas y opción 0

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-G-01 | Ninguna | 9, abc, (vacío), luego 0 | "Opción no válida." en cada entrada inválida y vuelve a pedir la opción | Pasa | 10/11/2026 | `Opción no válida.` (×3 tras 9, abc y vacío); `¡Hasta luego!` |
| CP-G-02 | Ninguna | Fin de entrada (cerrar stdin) | "Entrada finalizada." y termina sin errores | Pasa | 10/11/2026 | `Entrada finalizada.` (código de salida 0) |
| CP-G-03 | Ninguna | 2, Taladro, 0; reiniciar; 1 | Taladro aparece en el listado tras reiniciar | Pasa | 10/11/2026 | Run1: `Herramienta 'Taladro' añadida con ID 1.`; Run2: `1 Taladro Disponible` (CSV `1,Taladro,false`) |
| CP-00-01 | Ninguna | 0 | "¡Hasta luego!" y el programa termina | Pasa | 10/11/2026 | `¡Hasta luego!` |

## Opción 1: Ver herramientas y disponibilidad

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-01-01 | Sin herramientas | 1 | "No hay herramientas registradas." | Pasa | 10/11/2026 | `No hay herramientas registradas.` |
| CP-01-02 | Taladro activa; Escalera dada de baja | 1 | Taladro: Disponible. Escalera: Dada de baja | Pasa | 10/11/2026 | `1 Taladro Disponible` / `2 Escalera Dada de baja` |
| CP-01-03 | Taladro prestada a Ana hasta 2026-11-12 | 1 | Taladro: "Prestada a Ana (hasta 2026-11-12)" | Pasa | 10/11/2026 | `1 Taladro Prestada a Ana (hasta 2026-11-12)` |

## Opción 2: Añadir herramienta

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-02-01 | Ninguna | 2, Taladro | "añadida con ID 1"; se guarda 1, Taladro,false | Pasa | 10/11/2026 | `Herramienta 'Taladro' añadida con ID 1.` (CSV `1,Taladro,false`) |
| CP-02-02 | Ninguna | 2, (vacío), Escalera | "El campo no puede estar vacío."; después se añade con ID 1 | Pasa | 10/11/2026 | `El campo no puede estar vacío.` luego `Herramienta 'Escalera' añadida con ID 1.` |
| CP-02-03 | Ninguna | 2, (solo espacios), Escalera | Igual que CP-02-02 | Pasa | 10/11/2026 | `El campo no puede estar vacío.` luego `Herramienta 'Escalera' añadida con ID 1.` |
| CP-02-04 | Dos herramientas existentes | 2, Sierra | Se asigna ID 3 | Pasa | 10/11/2026 | `Herramienta 'Sierra' añadida con ID 3.` |
| CP-02-05 | Ninguna | 2, " Escalera " | Se muestra "Escalera" sin espacios sobrantes | Pasa | 10/11/2026 | `Herramienta 'Escalera' añadida con ID 1.` |

## Opción 3: Prestar herramienta

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-03-01 | Sin herramientas disponibles | 3 | "No hay herramientas disponibles para prestar." | Pasa | 10/11/2026 | `No hay herramientas disponibles para prestar.` |
| CP-03-02 | Taladro disponible | 3, 1, Ana, (Enter), (Enter) | Inicio hoy y límite hoy + 7 días | Pasa | 10/11/2026 | `Taladro prestada a Ana hasta 2026-11-17` (CSV `1,1,Ana,2026-11-10,2026-11-17`) |
| CP-03-03 | Taladro disponible | 3, 1, Ana, 2026-11-01, 2026-11-08 | Se guardan esas fechas exactas | Pasa | 10/11/2026 | `Taladro prestada a Ana hasta 2026-11-08` (CSV `1,1,Ana,2026-11-01,2026-11-08`) |
| CP-03-04 | Taladro prestada | 3 | Taladro no aparece; si se introduce su ID: "Opción no válida." | Pasa | 10/11/2026 | `Opción no válida.` (al introducir ID 1 de Taladro prestada) |
| CP-03-05 | Taladro disponible | 3, 1, (vacío) | "El campo no puede estar vacío." | Pasa | 10/11/2026 | `El campo no puede estar vacío.` |
| CP-03-06 | Taladro disponible | 3, 1, Ana, 01/11/2026 | "Formato inválido. Usa AAAA-MM-DD." | Pasa | 10/11/2026 | `Formato inválido. Usa AAAA-MM-DD.` |
| CP-03-07 | Taladro disponible | 3, 1, Ana, 2026-11-05, 2026-11-01 | "La fecha límite no puede ser anterior al préstamo."; no se guarda | Pasa | 10/11/2026 | `  Error: La fecha límite no puede ser anterior al préstamo` (sin préstamo guardado) |
| CP-03-08 | Taladro disponible | 3, 1, Ana, 2026-11-05, 2026-11-05 | Se acepta; límite igual al inicio | Pasa | 10/11/2026 | `Taladro prestada a Ana hasta 2026-11-05` |

## Opción 4: Registrar devolución

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-04-01 | Sin préstamos activos | 4 | "No hay préstamos pendientes de devolución." | Pasa | 10/11/2026 | `No hay préstamos pendientes de devolución.` |
| CP-04-02 | Límite 2026-11-08 | 4, 1, 2026-11-07 | "Devuelta a tiempo, sin penalización." | Pasa | 10/11/2026 | `Devuelta a tiempo, sin penalización.` |
| CP-04-03 | Límite 2026-11-08 | 4, 1, 2026-11-11 | "Retraso de 3 día(s). Penalización: 6.00 €" | Pasa | 10/11/2026 | `Retraso de 3 día(s). Penalización: 6.00 €` |
| CP-04-04 | Límite 2026-11-08 | 4, 1, 2026-11-08 | Sin retraso: "Devuelta a tiempo, sin penalización." | Pasa | 10/11/2026 | `Devuelta a tiempo, sin penalización.` |
| CP-04-05 | Inicio 2026-11-05 | 4, 1, 2026-11-01 | "La devolución no puede ser anterior al préstamo."; sigue activo | Pasa | 10/11/2026 | `  Error: La devolución no puede ser anterior al préstamo`; préstamo sigue activo |
| CP-04-06 | Préstamo activo con ID 1 | 4, 5 | "Opción no válida." | Pasa | 10/11/2026 | `Opción no válida.` |
| CP-04-07 | Préstamo activo | 4, 1, (Enter) | Fecha de devolución = hoy; retraso calculado según hoy | Pasa | 10/11/2026 | `Retraso de 2 día(s). Penalización: 4.00 €` (devolución = hoy 2026-11-10) |
| CP-04-08 | Préstamo ya devuelto | 4 | El préstamo devuelto no aparece en la lista | Pasa | 10/11/2026 | `No hay préstamos pendientes de devolución.` |

## Opción 5: Ver préstamos activos

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-05-01 | Sin préstamos activos | 5 | "No hay préstamos activos." | Pasa | 10/11/2026 | `No hay préstamos activos.` |
| CP-05-02 | Límite 2026-12-01 | 5 | "Al día, vence 2026-12-01" | Pasa | 10/11/2026 | `Al día, vence 2026-12-01` |
| CP-05-03 | Límite 2026-10-30; hoy 2026-11-05 | 5 | "ATRASADO 6 día(s), penalización estimada 12.00 €" | Pasa | 10/11/2026 | `ATRASADO 11 día(s), penalización estimada 22.00 €` (ejecutado 2026-11-10) |
| CP-05-04 | Préstamo devuelto | 5 | No aparece en la lista | Pasa | 10/11/2026 | Listado activo muestra `Martillo - Luis`; Taladro (devuelto) ausente |

## Opción 6: Ver historial y penalizaciones

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-06-01 | Sin devoluciones | 6 | "No hay devoluciones registradas." | Pasa | 10/11/2026 | `No hay devoluciones registradas.` |
| CP-06-02 | Dos devoluciones (3 días y 0 días; la primera 6.00 €) | 6 | Dos líneas y "Total de penalizaciones cobradas: 6.00 €" | Pasa | 10/11/2026 | Dos líneas + `Total de penalizaciones cobradas: 6.00 €` |
| CP-06-03 | Un préstamo activo y uno devuelto | 6 | Solo aparece el devuelto | Pasa | 10/11/2026 | `Historial de devoluciones:` muestra solo Taladro (devuelto) |

## Opción 7: Dar de baja una herramienta

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-07-01 | Todas las herramientas dadas de baja | 7 | "No hay herramientas que dar de baja." | Pasa | 10/11/2026 | `No hay herramientas que dar de baja.` |
| CP-07-02 | Escalera disponible | 7, 2 | "dada de baja. Su historial se conserva."; se guarda true; deja de aparecer en la opción 3 | Pasa | 10/11/2026 | `Escalera dada de baja. Su historial se conserva.` (CSV `2,Escalera,true`; ausente en opción 3) |
| CP-07-03 | Taladro prestada | 7, 1 | "No se puede dar de baja: la herramienta está prestada."; sin cambios | Pasa | 10/11/2026 | `  Error: No se puede dar de baja: la herramienta está prestada` (CSV `1,Taladro,false`) |
| CP-07-04 | Escalera ya dada de baja | 7 | No aparece; si se introduce su ID: "Opción no válida." | Pasa | 10/11/2026 | `Opción no válida.` (al introducir ID 2 de Escalera dada de baja) |
| CP-07-05 | Herramienta con préstamos antiguos | 7, ID de esa herramienta | Los préstamos siguen en el historial | Pasa | 10/11/2026 | `Historial de devoluciones:` sigue mostrando el préstamo tras la baja |

## Opción 8: Reactivar herramienta

| ID | Precondición | Entrada | Resultado esperado | Pasa / No Pasa | Fecha de ejecución | Resultado mostrado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| CP-08-01 | Ninguna dada de baja | 8 | "No hay herramientas dadas de baja." | Pasa | 10/11/2026 | `No hay herramientas dadas de baja.` |
| CP-08-02 | Escalera dada de baja | 8, 2 | "reactivada y disponible para préstamo."; se guarda false | Pasa | 10/11/2026 | `Escalera reactivada y disponible para préstamo.` (CSV `2,Escalera,false`) |
| CP-08-03 | Taladro activa | 8 | No aparece; si se introduce su ID: "Opción no válida." | Pasa | 10/11/2026 | `Opción no válida.` (al introducir ID 1 de Taladro activa) |
| CP-08-04 | Herramienta con historial | 8, ID de esa herramienta | El historial de la opción 6 no cambia | Pasa | 10/11/2026 | `Historial de devoluciones:` sin cambios tras reactivar |