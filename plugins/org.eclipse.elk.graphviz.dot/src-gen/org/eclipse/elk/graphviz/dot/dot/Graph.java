/**
 */
package org.eclipse.elk.graphviz.dot.dot;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.elk.graphviz.dot.dot.Graph#isStrict <em>Strict</em>}</li>
 *   <li>{@link org.eclipse.elk.graphviz.dot.dot.Graph#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.elk.graphviz.dot.dot.Graph#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.elk.graphviz.dot.dot.Graph#getStatements <em>Statements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.elk.graphviz.dot.dot.DotPackage#getGraph()
 * @model
 * @generated
 */
public interface Graph extends EObject
{
  /**
   * Returns the value of the '<em><b>Strict</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Strict</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Strict</em>' attribute.
   * @see #setStrict(boolean)
   * @see org.eclipse.elk.graphviz.dot.dot.DotPackage#getGraph_Strict()
   * @model
   * @generated
   */
  boolean isStrict();

  /**
   * Sets the value of the '{@link org.eclipse.elk.graphviz.dot.dot.Graph#isStrict <em>Strict</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Strict</em>' attribute.
   * @see #isStrict()
   * @generated
   */
  void setStrict(boolean value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * The literals are from the enumeration {@link org.eclipse.elk.graphviz.dot.dot.GraphType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see org.eclipse.elk.graphviz.dot.dot.GraphType
   * @see #setType(GraphType)
   * @see org.eclipse.elk.graphviz.dot.dot.DotPackage#getGraph_Type()
   * @model
   * @generated
   */
  GraphType getType();

  /**
   * Sets the value of the '{@link org.eclipse.elk.graphviz.dot.dot.Graph#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see org.eclipse.elk.graphviz.dot.dot.GraphType
   * @see #getType()
   * @generated
   */
  void setType(GraphType value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.eclipse.elk.graphviz.dot.dot.DotPackage#getGraph_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.elk.graphviz.dot.dot.Graph#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Statements</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.elk.graphviz.dot.dot.Statement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Statements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Statements</em>' containment reference list.
   * @see org.eclipse.elk.graphviz.dot.dot.DotPackage#getGraph_Statements()
   * @model containment="true"
   * @generated
   */
  EList<Statement> getStatements();

} // Graph
