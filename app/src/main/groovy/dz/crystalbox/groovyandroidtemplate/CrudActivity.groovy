package dz.crystalbox.groovyandroidtemplate

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.Extra
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.SaveInstance
import com.arasthel.swissknife.dsl.components.GArrayAdapter
import com.orm.SugarRecord
import dz.crystalbox.groovyandroidtemplate.R
import dz.crystalbox.groovyandroidtemplate.component.*
import dz.crystalbox.groovyandroidtemplate.model.Person
import groovy.transform.CompileStatic

import javax.inject.Inject

import static dz.crystalbox.groovyandroidtemplate.helper.ContextMethods.*

@CompileStatic
class CrudActivity extends AppCompatActivity {

    @InjectView(R.id.list_person)
    ListView listPerson

    @InjectView(R.id.edUserName)
    EditText userName

    @InjectView(R.id.edUserAge)
    EditText userAge

    @Extra
    String api_key // extra value for demo

    @Inject
    public SugarRecord sugar

    @SaveInstance
    Person personInstance

    GArrayAdapter adapter



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        SwissKnife.inject(this)

        SwissKnife.loadExtras(this)

        SwissKnife.restoreState(this, savedInstanceState)

        // inject SugarRecord
        AppComponent appInjector = DaggerAppComponent.builder().build()
        appInjector.inject(this)

        // injected with @Extra and SwissKnife.loadExtras(this), no need to write:
//        String api_key = getIntent().getStringExtra("api_key")
        log "api_key: ${api_key}"

        // setup GArrayAdapter
        listPerson.onItem(R.layout.item_list_row, sugar.listAll(Person)){ Person person, View view, int position ->
            TextView personView = view.text(R.id.txtPerson){
                it.text = person.toString()
            } as TextView
            personView.onClick {
                userName.setText( person.name )
                userAge.setText( String.valueOf(person.age) )
                personInstance = person
                // Send item to another activity for edition
//                Intent i = new Intent(this, PersonActivity)
//                i.putExtra('person', person as Parcelable)
//                i.putExtra('item_id', position)
//                startActivityForResult(i, PERSON_EDIT)
            }
            view.button(R.id.btnDelete){
                it.onClick {
                    alert(context){
                        title = "Delete?"
                        message = "Do you want to delete?"
                        cancelable = true
                        setPositiveButton('Yes', { DialogInterface dialog, int which ->
                            deletePerson(person)
                            log "Nbr of Person: ${sugar.count(Person)}"
                        })
                        setNegativeButton('No', null)

                    }
                }
            }
        }
        // save a reference to the adapter
        adapter = listPerson.adapter as GArrayAdapter

    }

    def deletePerson(Person p){
        if (p.delete())
            adapter.remove(p)
    }

    @OnClick(R.id.btnSave)
    public savePerson(View v) {
        String name = userName.text
        int age = userAge.text.toInteger()
        boolean newObj = (personInstance == null)
        if (newObj) {
            personInstance = new Person(name, age)
        } else {
            personInstance.name = name
            personInstance.age = age
        }
        if (personInstance.save()) {
            adapter.with {
                if (newObj)
                    add(personInstance)
                notifyDataSetChanged()
            }
            personInstance = null
            userName.text = ''
            userAge.text = ''
        }
    }

}
