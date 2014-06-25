/**
 */
package tp5.dslexterno.xtext.planificacionMaterias.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import tp5.dslexterno.xtext.planificacionMaterias.Dedicacion;
import tp5.dslexterno.xtext.planificacionMaterias.PlanificacionMateriasPackage;
import tp5.dslexterno.xtext.planificacionMaterias.Profesor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Profesor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tp5.dslexterno.xtext.planificacionMaterias.impl.ProfesorImpl#getNombre <em>Nombre</em>}</li>
 *   <li>{@link tp5.dslexterno.xtext.planificacionMaterias.impl.ProfesorImpl#getDedicacion <em>Dedicacion</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProfesorImpl extends MinimalEObjectImpl.Container implements Profesor
{
  /**
   * The default value of the '{@link #getNombre() <em>Nombre</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNombre()
   * @generated
   * @ordered
   */
  protected static final String NOMBRE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNombre() <em>Nombre</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNombre()
   * @generated
   * @ordered
   */
  protected String nombre = NOMBRE_EDEFAULT;

  /**
   * The cached value of the '{@link #getDedicacion() <em>Dedicacion</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDedicacion()
   * @generated
   * @ordered
   */
  protected Dedicacion dedicacion;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ProfesorImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return PlanificacionMateriasPackage.Literals.PROFESOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getNombre()
  {
    return nombre;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNombre(String newNombre)
  {
    String oldNombre = nombre;
    nombre = newNombre;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PlanificacionMateriasPackage.PROFESOR__NOMBRE, oldNombre, nombre));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Dedicacion getDedicacion()
  {
    return dedicacion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDedicacion(Dedicacion newDedicacion, NotificationChain msgs)
  {
    Dedicacion oldDedicacion = dedicacion;
    dedicacion = newDedicacion;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PlanificacionMateriasPackage.PROFESOR__DEDICACION, oldDedicacion, newDedicacion);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDedicacion(Dedicacion newDedicacion)
  {
    if (newDedicacion != dedicacion)
    {
      NotificationChain msgs = null;
      if (dedicacion != null)
        msgs = ((InternalEObject)dedicacion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PlanificacionMateriasPackage.PROFESOR__DEDICACION, null, msgs);
      if (newDedicacion != null)
        msgs = ((InternalEObject)newDedicacion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PlanificacionMateriasPackage.PROFESOR__DEDICACION, null, msgs);
      msgs = basicSetDedicacion(newDedicacion, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PlanificacionMateriasPackage.PROFESOR__DEDICACION, newDedicacion, newDedicacion));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case PlanificacionMateriasPackage.PROFESOR__DEDICACION:
        return basicSetDedicacion(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case PlanificacionMateriasPackage.PROFESOR__NOMBRE:
        return getNombre();
      case PlanificacionMateriasPackage.PROFESOR__DEDICACION:
        return getDedicacion();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case PlanificacionMateriasPackage.PROFESOR__NOMBRE:
        setNombre((String)newValue);
        return;
      case PlanificacionMateriasPackage.PROFESOR__DEDICACION:
        setDedicacion((Dedicacion)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case PlanificacionMateriasPackage.PROFESOR__NOMBRE:
        setNombre(NOMBRE_EDEFAULT);
        return;
      case PlanificacionMateriasPackage.PROFESOR__DEDICACION:
        setDedicacion((Dedicacion)null);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case PlanificacionMateriasPackage.PROFESOR__NOMBRE:
        return NOMBRE_EDEFAULT == null ? nombre != null : !NOMBRE_EDEFAULT.equals(nombre);
      case PlanificacionMateriasPackage.PROFESOR__DEDICACION:
        return dedicacion != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (nombre: ");
    result.append(nombre);
    result.append(')');
    return result.toString();
  }

} //ProfesorImpl
