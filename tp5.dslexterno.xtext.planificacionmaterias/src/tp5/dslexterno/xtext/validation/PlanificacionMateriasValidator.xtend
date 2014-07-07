/*
 * generated by Xtext
 */
package tp5.dslexterno.xtext.validation

import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.validation.Check
import tp5.dslexterno.xtext.planificacionMaterias.Asignacion_Diaria
import tp5.dslexterno.xtext.planificacionMaterias.Asignacion_Materia
import tp5.dslexterno.xtext.planificacionMaterias.Dia
import tp5.dslexterno.xtext.planificacionMaterias.Disponibilidad
import tp5.dslexterno.xtext.planificacionMaterias.Disponible
import tp5.dslexterno.xtext.planificacionMaterias.Exclusiva
import tp5.dslexterno.xtext.planificacionMaterias.Horario
import tp5.dslexterno.xtext.planificacionMaterias.No_Disponible
import tp5.dslexterno.xtext.planificacionMaterias.Planificacion
import tp5.dslexterno.xtext.planificacionMaterias.PlanificacionMateriasPackage
import tp5.dslexterno.xtext.planificacionMaterias.Profesor
import tp5.dslexterno.xtext.planificacionMaterias.Rango_Horario
import tp5.dslexterno.xtext.planificacionMaterias.Semi
import tp5.dslexterno.xtext.planificacionMaterias.Simple

/**
 * Custom validation rules. 
 *
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
class PlanificacionMateriasValidator extends AbstractPlanificacionMateriasValidator {


	/*
	 * Validaciones iniciales
	 */
	@Check
	def validarCargaHorariaDocente(Asignacion_Materia asignacionMateria){
		var listaDeAsignaciones = (asignacionMateria.eContainer as Planificacion).asignacionesDeMaterias
		var profesor = asignacionMateria.profesor
		if(profesor.cantMateriasSegunDedicacion < listaDeAsignaciones.materiasAsignadasA(profesor).size){
			val diferenciaMaterias = listaDeAsignaciones.materiasAsignadasA(profesor).size - profesor.cantMateriasSegunDedicacion
			error('''El profesor «profesor.name.toUpperCase» supera en «diferenciaMaterias» materia/s el limite de su dedicacion''', asignacionMateria,
				PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__PROFESOR)
		}
	}
	@Check
	def validarTodasLasMateriasAsignadas(Planificacion planificacion){
		val materiasADictar = planificacion.materiasADictar
		val materiasAsignadas = planificacion.asignacionesDeMaterias.map[materia]
		if(!materiasAsignadas.containsAll(materiasADictar)){
			val diferenciaMaterias = materiasADictar.filter[!materiasAsignadas.contains(it)]		
			error('''Falta asignar la/s siguiente/s materia/s: «diferenciaMaterias.forEach[materia | materia.name.toUpperCase]»''', planificacion,
				PlanificacionMateriasPackage.Literals.PLANIFICACION__MATERIAS_ADICTAR)
		}
	}
	@Check
	def validarCargaHorariaMateria(Asignacion_Materia asignacion){
		val diferenciaHoras = asignacion.horasAsignadas - asignacion.materia.cantidadHorasSemanales
		val diferenciaDias = asignacion.diasAsignados - asignacion.materia.diasSemanales
		if (diferenciaHoras < 0){
			error('''A la materia «asignacion.materia.name.toUpperCase» le falta asignar «(-diferenciaHoras).toString» horas''', asignacion,
				PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA)
		} 
		if(diferenciaHoras > 0){
			error('''La materia «asignacion.materia.name.toUpperCase» excede en «diferenciaHoras.toString» horas su carga semanal''', asignacion,
				PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA)
		}
		if(diferenciaDias < 0){
			error('''A la materia «asignacion.materia.name.toUpperCase» le falta asignar «(-diferenciaDias).toString» dias''', asignacion,
				PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA)
		}
		if(diferenciaDias > 0){
			error('''A la materia «asignacion.materia.name.toUpperCase» le falta asignar «diferenciaDias.toString» dias''', asignacion,
				PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA)
		}
	}
	@Check
	// Pisa a la validacion de superposicion entre materias
	def validarCompatibilidadAulaMateria(Asignacion_Materia asignacion){
		val aula = asignacion.aula
		val recursosAula = aula.recusos.map[name]
		val requerimientosMateria = asignacion.materia.requerimientos.map[name]
		if (!recursosAula.containsAll(requerimientosMateria)){
			error('''«aula.name.toUpperCase»  no cuenta con los recursos requeridos por la materia «asignacion.materia.name.toUpperCase»''', asignacion,
				PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__AULA)
		}
	}
	@Check
	def validarSuperposicionEntreMaterias(Asignacion_Materia asignacion){
		var listaAsignacionesEnMismaAula = (asignacion.eContainer as Planificacion).asignacionesDeMaterias.filter[aula == asignacion.aula].toList
		if(listaAsignacionesEnMismaAula.seSuperponeConAlguno(asignacion)){
			error('''«asignacion.aula.name.toUpperCase» se encuentra asignada en el horario solicitado''', asignacion,
				PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA)
		}
	}

	/*
	 * Validaciones de puntos bonus
	 */
	@Check
	def validarCapacidadAula(Asignacion_Materia asignacion){
		val alumnosInscriptos = asignacion.alumnosInscriptos
		if(asignacion.aula.capacidad < alumnosInscriptos){
			error('''«asignacion.aula.name.toUpperCase»  no tiene capacidad para la cantidad de alumnos inscriptos.''', asignacion,
				PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__AULA)
		}
	}
	@Check
	def validarDisponibilidadProfesor(Asignacion_Materia asignacion){
		var horariosMateria = asignacion.asignacionesDiarias
		if(!asignacion.profesor.estaDisponibleParaLosHorarios(horariosMateria)){
			error('''«asignacion.profesor.name.toUpperCase» no tiene disponibilidad para la materia asignada''', asignacion,
				PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__PROFESOR)
		}
	}

	
	/*
	 * Validaciones extras
	 */
	@Check
	def validarDeclaracionRangoHorario(Rango_Horario rango){
		if (rango.horaInicio.hora == rango.horaFinal.hora){
			if (rango.horaInicio.minutos >= rango.horaFinal.minutos){
				error('''El horario de INICIO del rango es posterior al horario de FINALIZACION por: «rango.horaInicio.minutos - rango.horaFinal.minutos» minutos''', rango,
					PlanificacionMateriasPackage.Literals.RANGO_HORARIO__HORA_INICIO)
			} 
		} else {
			if(rango.horaInicio.hora >= rango.horaFinal.hora){
				error('''El horario de INICIO del rango es posterior al horario de FINALIZACION por «rango.horaInicio.hora - rango.horaFinal.hora» horas''', rango,
					PlanificacionMateriasPackage.Literals.RANGO_HORARIO__HORA_INICIO)
			}
		}
	}
	
	//TODO: Consultar como puedo saber si tengo un elemento repetido...
//	@Check
//	def validarDeclaracionDisponibilidad(Profesor profesor){
//		val disponibilidadDeclarada = profesor.disponibilidad
//		if(disponibilidadDeclarada.hayRepetidas){
//			error('''Hay disponibilidades repetidas''', profesor,
//				PlanificacionMateriasPackage.Literals.PROFESOR__DISPONIBILIDAD)
//		}
//	}
//	def boolean hayRepetidas(EList<Disponibilidad> disponibilidades){
////		disponibilidades.filter[disponibilidad | disponibilidad.repetida(disponibilidades)].size > 0
//		false		
//	}
//	
	/*
	 * Comportamiento agregado via extension methods, como todo buen ser humano...
	 */
	 
	// ASIGANCION_MATERIA
	def int horasAsignadas(Asignacion_Materia asignacion) {
		asignacion.asignacionesDiarias.map[rangoHorario.cantidadDeHoras].reduce[sum, cantHoras | sum + cantHoras]
	}
	
	def int diasAsignados(Asignacion_Materia asignacion){
		asignacion.asignacionesDiarias.map[dia].size
	}	
	
	def dispatch boolean seSuperponeConAlguno(List<Asignacion_Materia> asignacionesMaterias, Asignacion_Materia asignacion){
		asignacionesMaterias.remove(asignacion)
		val listaAsignacionesDiariasDePlanificacion = asignacionesMaterias.map[asignacionesDiarias].flatten
		listaAsignacionesDiariasDePlanificacion.exists[seSuperponeConAlguno(asignacion.asignacionesDiarias)]
	}
	
	def dispatch boolean seSuperponeConAlguno(Asignacion_Diaria asignacionDeDia, List<Asignacion_Diaria> listaAsignaciones){
		listaAsignaciones.exists[rangoHorario.seSuperponeCon(asignacionDeDia.rangoHorario) && dia == asignacionDeDia.dia]
	}
	
	def boolean estaDisponibleParaLosHorarios(Profesor profesor, EList<Asignacion_Diaria> horarios){
		var noDisponible = profesor.disponibilidad.filter[!estaDisponible].toList
		var disponible = profesor.disponibilidad.filter[estaDisponible].toList
		if(noDisponible.empty){
			horarios.estanContenidosEn(disponible)
		} else{
			if(disponible.empty){
				!noDisponible.coincideCon(horarios)
			} else {
				!noDisponible.coincideCon(horarios) && horarios.estanContenidosEn(disponible)
			}
		}
	}
	
	def boolean coincideCon(List<Disponibilidad> noDisponibilidades, EList<Asignacion_Diaria> horariosMateria){
		val diasNoDisponible = noDisponibilidades.filter[esDeDiaCompleto].map[dia].toList
		val horariosNoDisponible= noDisponibilidades.filter[!esDeDiaCompleto].toList
		horariosMateria.exists[diasNoDisponible.contains(it.dia)] || 
			horariosMateria.exists[dentroDeLosRangos(horariosNoDisponible)]
	}
	
	def boolean esDeDiaCompleto(Disponibilidad disponibilidad){
		disponibilidad.rangosHorario == null
	}
	
	def boolean estanContenidosEn(EList<Asignacion_Diaria> listaDeHorariosGeneral, List<Disponibilidad> disponibilidades){
		val diasDisponibles = disponibilidades.filter[esDeDiaCompleto].toList
		val horariosDisponibles = disponibilidades.filter[!esDeDiaCompleto].toList
		var horariosGenerales = listaDeHorariosGeneral.filter[!diasDisponibles.map[dia].contains(it.dia)].toList
		horariosGenerales.forall[dentroDeLosRangos(horariosDisponibles)]
	}
	
	def boolean dentroDeLosRangos(Asignacion_Diaria asignacionDiaria, List<Disponibilidad> disponibilidades) {
		disponibilidades.exists[contiene(asignacionDiaria)]
	}
	
	def boolean contiene(Disponibilidad disponibilidad, Asignacion_Diaria asignacionDiaria) {
		disponibilidad.dia == asignacionDiaria.dia && asignacionDiaria.rangoHorario.dentroDelRango(disponibilidad.rangosHorario) 
	}
	
	def boolean dentroDelRango(Rango_Horario horarioInterior, Rango_Horario horario){
		horario.horaInicio <= horarioInterior.horaInicio && horarioInterior.horaFinal <= horario.horaFinal
	}	
		
	// PROFESOR
	def List<Asignacion_Materia> materiasAsignadasA(List<Asignacion_Materia> listaAsignaciones, Profesor profesor0){
		listaAsignaciones.filter[profesor == profesor0].toList
	}	
	def int cantMateriasSegunDedicacion(Profesor profesor) {
		profesor.dedicacion.cantidadMaterias
	}
	
	// DEDICACION
	def dispatch int cantidadMaterias(Simple dedicacion) { return 1 }
	def dispatch int cantidadMaterias(Semi dedicacion) { return 2 }
	def dispatch int cantidadMaterias(Exclusiva dedicacion) { return 5 }
	
	// DISPONIBILIDAD
	def dispatch boolean estaDisponible(Disponible disponibilidad){ true }
	def dispatch boolean estaDisponible(No_Disponible disponibilidad){ false }
	
	
	/*
	 * LA MAGIA DE MODELAR HORARIOS COMO LA GENTE, LPM :D 
	 * 
	 */
	//HORARIOS
	def <(Horario horario1, Horario horario2){
		horario1.hora < horario2.hora || (horario1.hora == horario2.hora && horario1.minutos < horario2.minutos)
	}	
	def >(Horario horario1, Horario horario2){
		horario1.hora > horario2.hora || (horario1.hora == horario2.hora && horario1.minutos > horario2.minutos)
	}	
	def <=(Horario horario1, Horario horario2){
		horario1.hora <= horario2.hora || (horario1.hora == horario2.hora && horario1.minutos <= horario2.minutos)
	}	
	def >=(Horario horario1, Horario horario2){
		horario1.hora >= horario2.hora || (horario1.hora == horario2.hora && horario1.minutos >= horario2.minutos)
	}	
	def dispatch toString(Horario horario){
		'''«horario.hora» : «horario.minutos»'''
	}
	//RANGOS HORARIOS
	def int cantidadDeHoras(Rango_Horario rangoHorario){
		rangoHorario.horaFinal.hora - rangoHorario.horaInicio.hora
	}
	def boolean seSuperponeCon(Rango_Horario rangoHorario1, Rango_Horario rangoHorario2){
		rangoHorario2.horaInicio.estaEntre(rangoHorario1) || rangoHorario2.horaFinal.estaEntre(rangoHorario1) 		
	}
	def boolean estaEntre(Horario horario, Rango_Horario rangoHorario){
		rangoHorario.horaInicio < horario && horario < rangoHorario.horaFinal 
	}
	def boolean abarcaRangoHorario(Rango_Horario rangoHorario, Rango_Horario rangoHorario2){
		rangoHorario.horaInicio < rangoHorario2.horaInicio && rangoHorario.horaFinal >= rangoHorario2.horaFinal 
	}
	def dispatch toString(Rango_Horario rangoHorario){
		'''de «rangoHorario.horaInicio» a «rangoHorario.horaFinal»'''
	}
	def equals(Dia dia1, Dia dia2){
		dia1 == dia2
	}
	def dispatch toString(Asignacion_Diaria asignacionDiaria){
		'''«asignacionDiaria.dia» «asignacionDiaria.rangoHorario»''' 
	}
}
