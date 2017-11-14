package dz.crystalbox.groovyandroidtemplate.model

import com.arasthel.swissknife.annotations.Parcelable
import com.orm.SugarRecord
import com.orm.dsl.Table
import groovy.transform.Canonical;
import groovy.transform.CompileStatic
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@CompileStatic
@Canonical
@ToString(includePackage = false)
//@Builder
@TupleConstructor
@Parcelable //(exclude={anotherObject;andThisToo}) // you can exclude other objects to avoid circular dependencies
@Table(name = 'users')
class Person extends SugarRecord {

//    @DatabaseField(generatedId = true)
//    long id

    String name
    int age


}
