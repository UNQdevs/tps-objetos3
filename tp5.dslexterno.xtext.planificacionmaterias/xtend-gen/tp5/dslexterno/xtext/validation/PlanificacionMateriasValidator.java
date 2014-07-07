/**
 * generated by Xtext
 */
package tp5.dslexterno.xtext.validation;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import tp5.dslexterno.xtext.planificacionMaterias.Asignacion_Diaria;
import tp5.dslexterno.xtext.planificacionMaterias.Asignacion_Materia;
import tp5.dslexterno.xtext.planificacionMaterias.Aula;
import tp5.dslexterno.xtext.planificacionMaterias.Dedicacion;
import tp5.dslexterno.xtext.planificacionMaterias.Dia;
import tp5.dslexterno.xtext.planificacionMaterias.Disponibilidad;
import tp5.dslexterno.xtext.planificacionMaterias.Disponible;
import tp5.dslexterno.xtext.planificacionMaterias.Exclusiva;
import tp5.dslexterno.xtext.planificacionMaterias.Horario;
import tp5.dslexterno.xtext.planificacionMaterias.Materia;
import tp5.dslexterno.xtext.planificacionMaterias.No_Disponible;
import tp5.dslexterno.xtext.planificacionMaterias.Planificacion;
import tp5.dslexterno.xtext.planificacionMaterias.PlanificacionMateriasPackage;
import tp5.dslexterno.xtext.planificacionMaterias.Profesor;
import tp5.dslexterno.xtext.planificacionMaterias.Rango_Horario;
import tp5.dslexterno.xtext.planificacionMaterias.Recurso;
import tp5.dslexterno.xtext.planificacionMaterias.Semi;
import tp5.dslexterno.xtext.planificacionMaterias.Simple;
import tp5.dslexterno.xtext.validation.AbstractPlanificacionMateriasValidator;

/**
 * Custom validation rules.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
@SuppressWarnings("all")
public class PlanificacionMateriasValidator extends AbstractPlanificacionMateriasValidator {
  /**
   * Validaciones iniciales
   */
  @Check
  public void validarCargaHorariaDocente(final Asignacion_Materia asignacionMateria) {
    EObject _eContainer = asignacionMateria.eContainer();
    EList<Asignacion_Materia> listaDeAsignaciones = ((Planificacion) _eContainer).getAsignacionesDeMaterias();
    Profesor profesor = asignacionMateria.getProfesor();
    int _cantMateriasSegunDedicacion = this.cantMateriasSegunDedicacion(profesor);
    List<Asignacion_Materia> _materiasAsignadasA = this.materiasAsignadasA(listaDeAsignaciones, profesor);
    int _size = _materiasAsignadasA.size();
    boolean _lessThan = (_cantMateriasSegunDedicacion < _size);
    if (_lessThan) {
      List<Asignacion_Materia> _materiasAsignadasA_1 = this.materiasAsignadasA(listaDeAsignaciones, profesor);
      int _size_1 = _materiasAsignadasA_1.size();
      int _cantMateriasSegunDedicacion_1 = this.cantMateriasSegunDedicacion(profesor);
      final int diferenciaMaterias = (_size_1 - _cantMateriasSegunDedicacion_1);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("El profesor ");
      String _name = profesor.getName();
      String _upperCase = _name.toUpperCase();
      _builder.append(_upperCase, "");
      _builder.append(" supera en ");
      _builder.append(diferenciaMaterias, "");
      _builder.append(" materia/s el limite de su dedicacion");
      this.error(_builder.toString(), asignacionMateria, 
        PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__PROFESOR);
    }
  }
  
  @Check
  public void validarTodasLasMateriasAsignadas(final Planificacion planificacion) {
    final EList<Materia> materiasADictar = planificacion.getMateriasADictar();
    EList<Asignacion_Materia> _asignacionesDeMaterias = planificacion.getAsignacionesDeMaterias();
    final Function1<Asignacion_Materia, Materia> _function = new Function1<Asignacion_Materia, Materia>() {
      public Materia apply(final Asignacion_Materia it) {
        return it.getMateria();
      }
    };
    final List<Materia> materiasAsignadas = ListExtensions.<Asignacion_Materia, Materia>map(_asignacionesDeMaterias, _function);
    boolean _containsAll = materiasAsignadas.containsAll(materiasADictar);
    boolean _not = (!_containsAll);
    if (_not) {
      final Function1<Materia, Boolean> _function_1 = new Function1<Materia, Boolean>() {
        public Boolean apply(final Materia it) {
          boolean _contains = materiasAsignadas.contains(it);
          return Boolean.valueOf((!_contains));
        }
      };
      final Iterable<Materia> diferenciaMaterias = IterableExtensions.<Materia>filter(materiasADictar, _function_1);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Falta asignar la/s siguiente/s materia/s: ");
      final Procedure1<Materia> _function_2 = new Procedure1<Materia>() {
        public void apply(final Materia materia) {
          String _name = materia.getName();
          _name.toUpperCase();
        }
      };
      IterableExtensions.<Materia>forEach(diferenciaMaterias, _function_2);
      this.error(_builder.toString(), planificacion, 
        PlanificacionMateriasPackage.Literals.PLANIFICACION__MATERIAS_ADICTAR);
    }
  }
  
  @Check
  public void validarCargaHorariaMateria(final Asignacion_Materia asignacion) {
    int _horasAsignadas = this.horasAsignadas(asignacion);
    Materia _materia = asignacion.getMateria();
    int _cantidadHorasSemanales = _materia.getCantidadHorasSemanales();
    final int diferenciaHoras = (_horasAsignadas - _cantidadHorasSemanales);
    int _diasAsignados = this.diasAsignados(asignacion);
    Materia _materia_1 = asignacion.getMateria();
    int _diasSemanales = _materia_1.getDiasSemanales();
    final int diferenciaDias = (_diasAsignados - _diasSemanales);
    if ((diferenciaHoras < 0)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("A la materia ");
      Materia _materia_2 = asignacion.getMateria();
      String _name = _materia_2.getName();
      String _upperCase = _name.toUpperCase();
      _builder.append(_upperCase, "");
      _builder.append(" le falta asignar ");
      String _string = Integer.valueOf((-diferenciaHoras)).toString();
      _builder.append(_string, "");
      _builder.append(" horas");
      this.error(_builder.toString(), asignacion, 
        PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA);
    }
    if ((diferenciaHoras > 0)) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("La materia ");
      Materia _materia_3 = asignacion.getMateria();
      String _name_1 = _materia_3.getName();
      String _upperCase_1 = _name_1.toUpperCase();
      _builder_1.append(_upperCase_1, "");
      _builder_1.append(" excede en ");
      String _string_1 = Integer.valueOf(diferenciaHoras).toString();
      _builder_1.append(_string_1, "");
      _builder_1.append(" horas su carga semanal");
      this.error(_builder_1.toString(), asignacion, 
        PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA);
    }
    if ((diferenciaDias < 0)) {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("A la materia ");
      Materia _materia_4 = asignacion.getMateria();
      String _name_2 = _materia_4.getName();
      String _upperCase_2 = _name_2.toUpperCase();
      _builder_2.append(_upperCase_2, "");
      _builder_2.append(" le falta asignar ");
      String _string_2 = Integer.valueOf((-diferenciaDias)).toString();
      _builder_2.append(_string_2, "");
      _builder_2.append(" dias");
      this.error(_builder_2.toString(), asignacion, 
        PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA);
    }
    if ((diferenciaDias > 0)) {
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("A la materia ");
      Materia _materia_5 = asignacion.getMateria();
      String _name_3 = _materia_5.getName();
      String _upperCase_3 = _name_3.toUpperCase();
      _builder_3.append(_upperCase_3, "");
      _builder_3.append(" le falta asignar ");
      String _string_3 = Integer.valueOf(diferenciaDias).toString();
      _builder_3.append(_string_3, "");
      _builder_3.append(" dias");
      this.error(_builder_3.toString(), asignacion, 
        PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA);
    }
  }
  
  @Check
  public void validarCompatibilidadAulaMateria(final Asignacion_Materia asignacion) {
    final Aula aula = asignacion.getAula();
    EList<Recurso> _recusos = aula.getRecusos();
    final Function1<Recurso, String> _function = new Function1<Recurso, String>() {
      public String apply(final Recurso it) {
        return it.getName();
      }
    };
    final List<String> recursosAula = ListExtensions.<Recurso, String>map(_recusos, _function);
    Materia _materia = asignacion.getMateria();
    EList<Recurso> _requerimientos = _materia.getRequerimientos();
    final Function1<Recurso, String> _function_1 = new Function1<Recurso, String>() {
      public String apply(final Recurso it) {
        return it.getName();
      }
    };
    final List<String> requerimientosMateria = ListExtensions.<Recurso, String>map(_requerimientos, _function_1);
    boolean _containsAll = recursosAula.containsAll(requerimientosMateria);
    boolean _not = (!_containsAll);
    if (_not) {
      StringConcatenation _builder = new StringConcatenation();
      String _name = aula.getName();
      String _upperCase = _name.toUpperCase();
      _builder.append(_upperCase, "");
      _builder.append("  no cuenta con los recursos requeridos por la materia ");
      Materia _materia_1 = asignacion.getMateria();
      String _name_1 = _materia_1.getName();
      String _upperCase_1 = _name_1.toUpperCase();
      _builder.append(_upperCase_1, "");
      this.error(_builder.toString(), asignacion, 
        PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__AULA);
    }
  }
  
  @Check
  public void validarSuperposicionEntreMaterias(final Asignacion_Materia asignacion) {
    EObject _eContainer = asignacion.eContainer();
    EList<Asignacion_Materia> _asignacionesDeMaterias = ((Planificacion) _eContainer).getAsignacionesDeMaterias();
    final Function1<Asignacion_Materia, Boolean> _function = new Function1<Asignacion_Materia, Boolean>() {
      public Boolean apply(final Asignacion_Materia it) {
        Aula _aula = it.getAula();
        Aula _aula_1 = asignacion.getAula();
        return Boolean.valueOf(Objects.equal(_aula, _aula_1));
      }
    };
    Iterable<Asignacion_Materia> _filter = IterableExtensions.<Asignacion_Materia>filter(_asignacionesDeMaterias, _function);
    List<Asignacion_Materia> listaAsignacionesEnMismaAula = IterableExtensions.<Asignacion_Materia>toList(_filter);
    boolean _seSuperponeConAlguno = this.seSuperponeConAlguno(listaAsignacionesEnMismaAula, asignacion);
    if (_seSuperponeConAlguno) {
      StringConcatenation _builder = new StringConcatenation();
      Aula _aula = asignacion.getAula();
      String _name = _aula.getName();
      String _upperCase = _name.toUpperCase();
      _builder.append(_upperCase, "");
      _builder.append(" se encuentra asignada en el horario solicitado");
      this.error(_builder.toString(), asignacion, 
        PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__MATERIA);
    }
  }
  
  /**
   * Validaciones de puntos bonus
   */
  @Check
  public void validarCapacidadAula(final Asignacion_Materia asignacion) {
    final int alumnosInscriptos = asignacion.getAlumnosInscriptos();
    Aula _aula = asignacion.getAula();
    int _capacidad = _aula.getCapacidad();
    boolean _lessThan = (_capacidad < alumnosInscriptos);
    if (_lessThan) {
      StringConcatenation _builder = new StringConcatenation();
      Aula _aula_1 = asignacion.getAula();
      String _name = _aula_1.getName();
      String _upperCase = _name.toUpperCase();
      _builder.append(_upperCase, "");
      _builder.append("  no tiene capacidad para la cantidad de alumnos inscriptos.");
      this.error(_builder.toString(), asignacion, 
        PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__AULA);
    }
  }
  
  @Check
  public void validarDisponibilidadProfesor(final Asignacion_Materia asignacion) {
    EList<Asignacion_Diaria> horariosMateria = asignacion.getAsignacionesDiarias();
    Profesor _profesor = asignacion.getProfesor();
    boolean _estaDisponibleParaLosHorarios = this.estaDisponibleParaLosHorarios(_profesor, horariosMateria);
    boolean _not = (!_estaDisponibleParaLosHorarios);
    if (_not) {
      StringConcatenation _builder = new StringConcatenation();
      Profesor _profesor_1 = asignacion.getProfesor();
      String _name = _profesor_1.getName();
      String _upperCase = _name.toUpperCase();
      _builder.append(_upperCase, "");
      _builder.append(" no tiene disponibilidad para la materia asignada");
      this.error(_builder.toString(), asignacion, 
        PlanificacionMateriasPackage.Literals.ASIGNACION_MATERIA__PROFESOR);
    }
  }
  
  /**
   * Validaciones extras
   */
  @Check
  public void validarDeclaracionRangoHorario(final Rango_Horario rango) {
    Horario _horaInicio = rango.getHoraInicio();
    int _hora = _horaInicio.getHora();
    Horario _horaFinal = rango.getHoraFinal();
    int _hora_1 = _horaFinal.getHora();
    boolean _equals = (_hora == _hora_1);
    if (_equals) {
      Horario _horaInicio_1 = rango.getHoraInicio();
      int _minutos = _horaInicio_1.getMinutos();
      Horario _horaFinal_1 = rango.getHoraFinal();
      int _minutos_1 = _horaFinal_1.getMinutos();
      boolean _greaterEqualsThan = (_minutos >= _minutos_1);
      if (_greaterEqualsThan) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("El horario de INICIO del rango es posterior al horario de FINALIZACION por: ");
        Horario _horaInicio_2 = rango.getHoraInicio();
        int _minutos_2 = _horaInicio_2.getMinutos();
        Horario _horaFinal_2 = rango.getHoraFinal();
        int _minutos_3 = _horaFinal_2.getMinutos();
        int _minus = (_minutos_2 - _minutos_3);
        _builder.append(_minus, "");
        _builder.append(" minutos");
        this.error(_builder.toString(), rango, 
          PlanificacionMateriasPackage.Literals.RANGO_HORARIO__HORA_INICIO);
      }
    } else {
      Horario _horaInicio_3 = rango.getHoraInicio();
      int _hora_2 = _horaInicio_3.getHora();
      Horario _horaFinal_3 = rango.getHoraFinal();
      int _hora_3 = _horaFinal_3.getHora();
      boolean _greaterEqualsThan_1 = (_hora_2 >= _hora_3);
      if (_greaterEqualsThan_1) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("El horario de INICIO del rango es posterior al horario de FINALIZACION por ");
        Horario _horaInicio_4 = rango.getHoraInicio();
        int _hora_4 = _horaInicio_4.getHora();
        Horario _horaFinal_4 = rango.getHoraFinal();
        int _hora_5 = _horaFinal_4.getHora();
        int _minus_1 = (_hora_4 - _hora_5);
        _builder_1.append(_minus_1, "");
        _builder_1.append(" horas");
        this.error(_builder_1.toString(), rango, 
          PlanificacionMateriasPackage.Literals.RANGO_HORARIO__HORA_INICIO);
      }
    }
  }
  
  /**
   * Comportamiento agregado via extension methods, como todo buen ser humano...
   */
  public int horasAsignadas(final Asignacion_Materia asignacion) {
    EList<Asignacion_Diaria> _asignacionesDiarias = asignacion.getAsignacionesDiarias();
    final Function1<Asignacion_Diaria, Integer> _function = new Function1<Asignacion_Diaria, Integer>() {
      public Integer apply(final Asignacion_Diaria it) {
        Rango_Horario _rangoHorario = it.getRangoHorario();
        return Integer.valueOf(PlanificacionMateriasValidator.this.cantidadDeHoras(_rangoHorario));
      }
    };
    List<Integer> _map = ListExtensions.<Asignacion_Diaria, Integer>map(_asignacionesDiarias, _function);
    final Function2<Integer, Integer, Integer> _function_1 = new Function2<Integer, Integer, Integer>() {
      public Integer apply(final Integer sum, final Integer cantHoras) {
        return Integer.valueOf(((sum).intValue() + (cantHoras).intValue()));
      }
    };
    return (int) IterableExtensions.<Integer>reduce(_map, _function_1);
  }
  
  public int diasAsignados(final Asignacion_Materia asignacion) {
    EList<Asignacion_Diaria> _asignacionesDiarias = asignacion.getAsignacionesDiarias();
    final Function1<Asignacion_Diaria, Dia> _function = new Function1<Asignacion_Diaria, Dia>() {
      public Dia apply(final Asignacion_Diaria it) {
        return it.getDia();
      }
    };
    List<Dia> _map = ListExtensions.<Asignacion_Diaria, Dia>map(_asignacionesDiarias, _function);
    return _map.size();
  }
  
  protected boolean _seSuperponeConAlguno(final List<Asignacion_Materia> asignacionesMaterias, final Asignacion_Materia asignacion) {
    boolean _xblockexpression = false;
    {
      asignacionesMaterias.remove(asignacion);
      final Function1<Asignacion_Materia, EList<Asignacion_Diaria>> _function = new Function1<Asignacion_Materia, EList<Asignacion_Diaria>>() {
        public EList<Asignacion_Diaria> apply(final Asignacion_Materia it) {
          return it.getAsignacionesDiarias();
        }
      };
      List<EList<Asignacion_Diaria>> _map = ListExtensions.<Asignacion_Materia, EList<Asignacion_Diaria>>map(asignacionesMaterias, _function);
      final Iterable<Asignacion_Diaria> listaAsignacionesDiariasDePlanificacion = Iterables.<Asignacion_Diaria>concat(_map);
      final Function1<Asignacion_Diaria, Boolean> _function_1 = new Function1<Asignacion_Diaria, Boolean>() {
        public Boolean apply(final Asignacion_Diaria it) {
          EList<Asignacion_Diaria> _asignacionesDiarias = asignacion.getAsignacionesDiarias();
          return Boolean.valueOf(PlanificacionMateriasValidator.this.seSuperponeConAlguno(it, _asignacionesDiarias));
        }
      };
      _xblockexpression = IterableExtensions.<Asignacion_Diaria>exists(listaAsignacionesDiariasDePlanificacion, _function_1);
    }
    return _xblockexpression;
  }
  
  protected boolean _seSuperponeConAlguno(final Asignacion_Diaria asignacionDeDia, final List<Asignacion_Diaria> listaAsignaciones) {
    final Function1<Asignacion_Diaria, Boolean> _function = new Function1<Asignacion_Diaria, Boolean>() {
      public Boolean apply(final Asignacion_Diaria it) {
        boolean _and = false;
        Rango_Horario _rangoHorario = it.getRangoHorario();
        Rango_Horario _rangoHorario_1 = asignacionDeDia.getRangoHorario();
        boolean _seSuperponeCon = PlanificacionMateriasValidator.this.seSuperponeCon(_rangoHorario, _rangoHorario_1);
        if (!_seSuperponeCon) {
          _and = false;
        } else {
          Dia _dia = it.getDia();
          Dia _dia_1 = asignacionDeDia.getDia();
          boolean _equals = Objects.equal(_dia, _dia_1);
          _and = _equals;
        }
        return Boolean.valueOf(_and);
      }
    };
    return IterableExtensions.<Asignacion_Diaria>exists(listaAsignaciones, _function);
  }
  
  public boolean estaDisponibleParaLosHorarios(final Profesor profesor, final EList<Asignacion_Diaria> horarios) {
    boolean _xblockexpression = false;
    {
      EList<Disponibilidad> _disponibilidad = profesor.getDisponibilidad();
      final Function1<Disponibilidad, Boolean> _function = new Function1<Disponibilidad, Boolean>() {
        public Boolean apply(final Disponibilidad it) {
          boolean _estaDisponible = PlanificacionMateriasValidator.this.estaDisponible(it);
          return Boolean.valueOf((!_estaDisponible));
        }
      };
      Iterable<Disponibilidad> _filter = IterableExtensions.<Disponibilidad>filter(_disponibilidad, _function);
      List<Disponibilidad> noDisponible = IterableExtensions.<Disponibilidad>toList(_filter);
      EList<Disponibilidad> _disponibilidad_1 = profesor.getDisponibilidad();
      final Function1<Disponibilidad, Boolean> _function_1 = new Function1<Disponibilidad, Boolean>() {
        public Boolean apply(final Disponibilidad it) {
          return Boolean.valueOf(PlanificacionMateriasValidator.this.estaDisponible(it));
        }
      };
      Iterable<Disponibilidad> _filter_1 = IterableExtensions.<Disponibilidad>filter(_disponibilidad_1, _function_1);
      List<Disponibilidad> disponible = IterableExtensions.<Disponibilidad>toList(_filter_1);
      boolean _xifexpression = false;
      boolean _isEmpty = noDisponible.isEmpty();
      if (_isEmpty) {
        _xifexpression = this.estanContenidosEn(horarios, disponible);
      } else {
        boolean _xifexpression_1 = false;
        boolean _isEmpty_1 = disponible.isEmpty();
        if (_isEmpty_1) {
          boolean _coincideCon = this.coincideCon(noDisponible, horarios);
          _xifexpression_1 = (!_coincideCon);
        } else {
          boolean _and = false;
          boolean _coincideCon_1 = this.coincideCon(noDisponible, horarios);
          boolean _not = (!_coincideCon_1);
          if (!_not) {
            _and = false;
          } else {
            boolean _estanContenidosEn = this.estanContenidosEn(horarios, disponible);
            _and = _estanContenidosEn;
          }
          _xifexpression_1 = _and;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public boolean coincideCon(final List<Disponibilidad> noDisponibilidades, final EList<Asignacion_Diaria> horariosMateria) {
    boolean _xblockexpression = false;
    {
      final Function1<Disponibilidad, Boolean> _function = new Function1<Disponibilidad, Boolean>() {
        public Boolean apply(final Disponibilidad it) {
          return Boolean.valueOf(PlanificacionMateriasValidator.this.esDeDiaCompleto(it));
        }
      };
      Iterable<Disponibilidad> _filter = IterableExtensions.<Disponibilidad>filter(noDisponibilidades, _function);
      final Function1<Disponibilidad, Dia> _function_1 = new Function1<Disponibilidad, Dia>() {
        public Dia apply(final Disponibilidad it) {
          return it.getDia();
        }
      };
      Iterable<Dia> _map = IterableExtensions.<Disponibilidad, Dia>map(_filter, _function_1);
      final List<Dia> diasNoDisponible = IterableExtensions.<Dia>toList(_map);
      final Function1<Disponibilidad, Boolean> _function_2 = new Function1<Disponibilidad, Boolean>() {
        public Boolean apply(final Disponibilidad it) {
          boolean _esDeDiaCompleto = PlanificacionMateriasValidator.this.esDeDiaCompleto(it);
          return Boolean.valueOf((!_esDeDiaCompleto));
        }
      };
      Iterable<Disponibilidad> _filter_1 = IterableExtensions.<Disponibilidad>filter(noDisponibilidades, _function_2);
      final List<Disponibilidad> horariosNoDisponible = IterableExtensions.<Disponibilidad>toList(_filter_1);
      boolean _or = false;
      final Function1<Asignacion_Diaria, Boolean> _function_3 = new Function1<Asignacion_Diaria, Boolean>() {
        public Boolean apply(final Asignacion_Diaria it) {
          Dia _dia = it.getDia();
          return Boolean.valueOf(diasNoDisponible.contains(_dia));
        }
      };
      boolean _exists = IterableExtensions.<Asignacion_Diaria>exists(horariosMateria, _function_3);
      if (_exists) {
        _or = true;
      } else {
        final Function1<Asignacion_Diaria, Boolean> _function_4 = new Function1<Asignacion_Diaria, Boolean>() {
          public Boolean apply(final Asignacion_Diaria it) {
            return Boolean.valueOf(PlanificacionMateriasValidator.this.dentroDeLosRangos(it, horariosNoDisponible));
          }
        };
        boolean _exists_1 = IterableExtensions.<Asignacion_Diaria>exists(horariosMateria, _function_4);
        _or = _exists_1;
      }
      _xblockexpression = _or;
    }
    return _xblockexpression;
  }
  
  public boolean esDeDiaCompleto(final Disponibilidad disponibilidad) {
    Rango_Horario _rangosHorario = disponibilidad.getRangosHorario();
    return Objects.equal(_rangosHorario, null);
  }
  
  public boolean estanContenidosEn(final EList<Asignacion_Diaria> listaDeHorariosGeneral, final List<Disponibilidad> disponibilidades) {
    boolean _xblockexpression = false;
    {
      final Function1<Disponibilidad, Boolean> _function = new Function1<Disponibilidad, Boolean>() {
        public Boolean apply(final Disponibilidad it) {
          return Boolean.valueOf(PlanificacionMateriasValidator.this.esDeDiaCompleto(it));
        }
      };
      Iterable<Disponibilidad> _filter = IterableExtensions.<Disponibilidad>filter(disponibilidades, _function);
      final List<Disponibilidad> diasDisponibles = IterableExtensions.<Disponibilidad>toList(_filter);
      final Function1<Disponibilidad, Boolean> _function_1 = new Function1<Disponibilidad, Boolean>() {
        public Boolean apply(final Disponibilidad it) {
          boolean _esDeDiaCompleto = PlanificacionMateriasValidator.this.esDeDiaCompleto(it);
          return Boolean.valueOf((!_esDeDiaCompleto));
        }
      };
      Iterable<Disponibilidad> _filter_1 = IterableExtensions.<Disponibilidad>filter(disponibilidades, _function_1);
      final List<Disponibilidad> horariosDisponibles = IterableExtensions.<Disponibilidad>toList(_filter_1);
      final Function1<Asignacion_Diaria, Boolean> _function_2 = new Function1<Asignacion_Diaria, Boolean>() {
        public Boolean apply(final Asignacion_Diaria it) {
          final Function1<Disponibilidad, Dia> _function = new Function1<Disponibilidad, Dia>() {
            public Dia apply(final Disponibilidad it) {
              return it.getDia();
            }
          };
          List<Dia> _map = ListExtensions.<Disponibilidad, Dia>map(diasDisponibles, _function);
          Dia _dia = it.getDia();
          boolean _contains = _map.contains(_dia);
          return Boolean.valueOf((!_contains));
        }
      };
      Iterable<Asignacion_Diaria> _filter_2 = IterableExtensions.<Asignacion_Diaria>filter(listaDeHorariosGeneral, _function_2);
      List<Asignacion_Diaria> horariosGenerales = IterableExtensions.<Asignacion_Diaria>toList(_filter_2);
      final Function1<Asignacion_Diaria, Boolean> _function_3 = new Function1<Asignacion_Diaria, Boolean>() {
        public Boolean apply(final Asignacion_Diaria it) {
          return Boolean.valueOf(PlanificacionMateriasValidator.this.dentroDeLosRangos(it, horariosDisponibles));
        }
      };
      _xblockexpression = IterableExtensions.<Asignacion_Diaria>forall(horariosGenerales, _function_3);
    }
    return _xblockexpression;
  }
  
  public boolean dentroDeLosRangos(final Asignacion_Diaria asignacionDiaria, final List<Disponibilidad> disponibilidades) {
    final Function1<Disponibilidad, Boolean> _function = new Function1<Disponibilidad, Boolean>() {
      public Boolean apply(final Disponibilidad it) {
        return Boolean.valueOf(PlanificacionMateriasValidator.this.contiene(it, asignacionDiaria));
      }
    };
    return IterableExtensions.<Disponibilidad>exists(disponibilidades, _function);
  }
  
  public boolean contiene(final Disponibilidad disponibilidad, final Asignacion_Diaria asignacionDiaria) {
    boolean _and = false;
    Dia _dia = disponibilidad.getDia();
    Dia _dia_1 = asignacionDiaria.getDia();
    boolean _equals = Objects.equal(_dia, _dia_1);
    if (!_equals) {
      _and = false;
    } else {
      Rango_Horario _rangoHorario = asignacionDiaria.getRangoHorario();
      Rango_Horario _rangosHorario = disponibilidad.getRangosHorario();
      boolean _dentroDelRango = this.dentroDelRango(_rangoHorario, _rangosHorario);
      _and = _dentroDelRango;
    }
    return _and;
  }
  
  public boolean dentroDelRango(final Rango_Horario horarioInterior, final Rango_Horario horario) {
    boolean _and = false;
    Horario _horaInicio = horario.getHoraInicio();
    Horario _horaInicio_1 = horarioInterior.getHoraInicio();
    boolean _lessEqualsThan = this.operator_lessEqualsThan(_horaInicio, _horaInicio_1);
    if (!_lessEqualsThan) {
      _and = false;
    } else {
      Horario _horaFinal = horarioInterior.getHoraFinal();
      Horario _horaFinal_1 = horario.getHoraFinal();
      boolean _lessEqualsThan_1 = this.operator_lessEqualsThan(_horaFinal, _horaFinal_1);
      _and = _lessEqualsThan_1;
    }
    return _and;
  }
  
  public List<Asignacion_Materia> materiasAsignadasA(final List<Asignacion_Materia> listaAsignaciones, final Profesor profesor0) {
    final Function1<Asignacion_Materia, Boolean> _function = new Function1<Asignacion_Materia, Boolean>() {
      public Boolean apply(final Asignacion_Materia it) {
        Profesor _profesor = it.getProfesor();
        return Boolean.valueOf(Objects.equal(_profesor, profesor0));
      }
    };
    Iterable<Asignacion_Materia> _filter = IterableExtensions.<Asignacion_Materia>filter(listaAsignaciones, _function);
    return IterableExtensions.<Asignacion_Materia>toList(_filter);
  }
  
  public int cantMateriasSegunDedicacion(final Profesor profesor) {
    Dedicacion _dedicacion = profesor.getDedicacion();
    return this.cantidadMaterias(_dedicacion);
  }
  
  protected int _cantidadMaterias(final Simple dedicacion) {
    return 1;
  }
  
  protected int _cantidadMaterias(final Semi dedicacion) {
    return 2;
  }
  
  protected int _cantidadMaterias(final Exclusiva dedicacion) {
    return 5;
  }
  
  protected boolean _estaDisponible(final Disponible disponibilidad) {
    return true;
  }
  
  protected boolean _estaDisponible(final No_Disponible disponibilidad) {
    return false;
  }
  
  /**
   * LA MAGIA DE MODELAR HORARIOS COMO LA GENTE, LPM :D
   */
  public boolean operator_lessThan(final Horario horario1, final Horario horario2) {
    boolean _or = false;
    int _hora = horario1.getHora();
    int _hora_1 = horario2.getHora();
    boolean _lessThan = (_hora < _hora_1);
    if (_lessThan) {
      _or = true;
    } else {
      boolean _and = false;
      int _hora_2 = horario1.getHora();
      int _hora_3 = horario2.getHora();
      boolean _equals = (_hora_2 == _hora_3);
      if (!_equals) {
        _and = false;
      } else {
        int _minutos = horario1.getMinutos();
        int _minutos_1 = horario2.getMinutos();
        boolean _lessThan_1 = (_minutos < _minutos_1);
        _and = _lessThan_1;
      }
      _or = _and;
    }
    return _or;
  }
  
  public boolean operator_greaterThan(final Horario horario1, final Horario horario2) {
    boolean _or = false;
    int _hora = horario1.getHora();
    int _hora_1 = horario2.getHora();
    boolean _greaterThan = (_hora > _hora_1);
    if (_greaterThan) {
      _or = true;
    } else {
      boolean _and = false;
      int _hora_2 = horario1.getHora();
      int _hora_3 = horario2.getHora();
      boolean _equals = (_hora_2 == _hora_3);
      if (!_equals) {
        _and = false;
      } else {
        int _minutos = horario1.getMinutos();
        int _minutos_1 = horario2.getMinutos();
        boolean _greaterThan_1 = (_minutos > _minutos_1);
        _and = _greaterThan_1;
      }
      _or = _and;
    }
    return _or;
  }
  
  public boolean operator_lessEqualsThan(final Horario horario1, final Horario horario2) {
    boolean _or = false;
    int _hora = horario1.getHora();
    int _hora_1 = horario2.getHora();
    boolean _lessEqualsThan = (_hora <= _hora_1);
    if (_lessEqualsThan) {
      _or = true;
    } else {
      boolean _and = false;
      int _hora_2 = horario1.getHora();
      int _hora_3 = horario2.getHora();
      boolean _equals = (_hora_2 == _hora_3);
      if (!_equals) {
        _and = false;
      } else {
        int _minutos = horario1.getMinutos();
        int _minutos_1 = horario2.getMinutos();
        boolean _lessEqualsThan_1 = (_minutos <= _minutos_1);
        _and = _lessEqualsThan_1;
      }
      _or = _and;
    }
    return _or;
  }
  
  public boolean operator_greaterEqualsThan(final Horario horario1, final Horario horario2) {
    boolean _or = false;
    int _hora = horario1.getHora();
    int _hora_1 = horario2.getHora();
    boolean _greaterEqualsThan = (_hora >= _hora_1);
    if (_greaterEqualsThan) {
      _or = true;
    } else {
      boolean _and = false;
      int _hora_2 = horario1.getHora();
      int _hora_3 = horario2.getHora();
      boolean _equals = (_hora_2 == _hora_3);
      if (!_equals) {
        _and = false;
      } else {
        int _minutos = horario1.getMinutos();
        int _minutos_1 = horario2.getMinutos();
        boolean _greaterEqualsThan_1 = (_minutos >= _minutos_1);
        _and = _greaterEqualsThan_1;
      }
      _or = _and;
    }
    return _or;
  }
  
  protected CharSequence _toString(final Horario horario) {
    StringConcatenation _builder = new StringConcatenation();
    int _hora = horario.getHora();
    _builder.append(_hora, "");
    _builder.append(" : ");
    int _minutos = horario.getMinutos();
    _builder.append(_minutos, "");
    return _builder;
  }
  
  public int cantidadDeHoras(final Rango_Horario rangoHorario) {
    Horario _horaFinal = rangoHorario.getHoraFinal();
    int _hora = _horaFinal.getHora();
    Horario _horaInicio = rangoHorario.getHoraInicio();
    int _hora_1 = _horaInicio.getHora();
    return (_hora - _hora_1);
  }
  
  public boolean seSuperponeCon(final Rango_Horario rangoHorario1, final Rango_Horario rangoHorario2) {
    boolean _or = false;
    Horario _horaInicio = rangoHorario2.getHoraInicio();
    boolean _estaEntre = this.estaEntre(_horaInicio, rangoHorario1);
    if (_estaEntre) {
      _or = true;
    } else {
      Horario _horaFinal = rangoHorario2.getHoraFinal();
      boolean _estaEntre_1 = this.estaEntre(_horaFinal, rangoHorario1);
      _or = _estaEntre_1;
    }
    return _or;
  }
  
  public boolean estaEntre(final Horario horario, final Rango_Horario rangoHorario) {
    boolean _and = false;
    Horario _horaInicio = rangoHorario.getHoraInicio();
    boolean _lessThan = this.operator_lessThan(_horaInicio, horario);
    if (!_lessThan) {
      _and = false;
    } else {
      Horario _horaFinal = rangoHorario.getHoraFinal();
      boolean _lessThan_1 = this.operator_lessThan(horario, _horaFinal);
      _and = _lessThan_1;
    }
    return _and;
  }
  
  public boolean abarcaRangoHorario(final Rango_Horario rangoHorario, final Rango_Horario rangoHorario2) {
    boolean _and = false;
    Horario _horaInicio = rangoHorario.getHoraInicio();
    Horario _horaInicio_1 = rangoHorario2.getHoraInicio();
    boolean _lessThan = this.operator_lessThan(_horaInicio, _horaInicio_1);
    if (!_lessThan) {
      _and = false;
    } else {
      Horario _horaFinal = rangoHorario.getHoraFinal();
      Horario _horaFinal_1 = rangoHorario2.getHoraFinal();
      boolean _greaterEqualsThan = this.operator_greaterEqualsThan(_horaFinal, _horaFinal_1);
      _and = _greaterEqualsThan;
    }
    return _and;
  }
  
  protected CharSequence _toString(final Rango_Horario rangoHorario) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("de ");
    Horario _horaInicio = rangoHorario.getHoraInicio();
    _builder.append(_horaInicio, "");
    _builder.append(" a ");
    Horario _horaFinal = rangoHorario.getHoraFinal();
    _builder.append(_horaFinal, "");
    return _builder;
  }
  
  public boolean equals(final Dia dia1, final Dia dia2) {
    return Objects.equal(dia1, dia2);
  }
  
  protected CharSequence _toString(final Asignacion_Diaria asignacionDiaria) {
    StringConcatenation _builder = new StringConcatenation();
    Dia _dia = asignacionDiaria.getDia();
    _builder.append(_dia, "");
    _builder.append(" ");
    Rango_Horario _rangoHorario = asignacionDiaria.getRangoHorario();
    _builder.append(_rangoHorario, "");
    return _builder;
  }
  
  public boolean seSuperponeConAlguno(final Object asignacionesMaterias, final Object asignacion) {
    if (asignacionesMaterias instanceof List
         && asignacion instanceof Asignacion_Materia) {
      return _seSuperponeConAlguno((List<Asignacion_Materia>)asignacionesMaterias, (Asignacion_Materia)asignacion);
    } else if (asignacionesMaterias instanceof Asignacion_Diaria
         && asignacion instanceof List) {
      return _seSuperponeConAlguno((Asignacion_Diaria)asignacionesMaterias, (List<Asignacion_Diaria>)asignacion);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(asignacionesMaterias, asignacion).toString());
    }
  }
  
  public int cantidadMaterias(final Dedicacion dedicacion) {
    if (dedicacion instanceof Exclusiva) {
      return _cantidadMaterias((Exclusiva)dedicacion);
    } else if (dedicacion instanceof Semi) {
      return _cantidadMaterias((Semi)dedicacion);
    } else if (dedicacion instanceof Simple) {
      return _cantidadMaterias((Simple)dedicacion);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(dedicacion).toString());
    }
  }
  
  public boolean estaDisponible(final Disponibilidad disponibilidad) {
    if (disponibilidad instanceof Disponible) {
      return _estaDisponible((Disponible)disponibilidad);
    } else if (disponibilidad instanceof No_Disponible) {
      return _estaDisponible((No_Disponible)disponibilidad);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(disponibilidad).toString());
    }
  }
  
  public CharSequence toString(final EObject asignacionDiaria) {
    if (asignacionDiaria instanceof Asignacion_Diaria) {
      return _toString((Asignacion_Diaria)asignacionDiaria);
    } else if (asignacionDiaria instanceof Horario) {
      return _toString((Horario)asignacionDiaria);
    } else if (asignacionDiaria instanceof Rango_Horario) {
      return _toString((Rango_Horario)asignacionDiaria);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(asignacionDiaria).toString());
    }
  }
}
