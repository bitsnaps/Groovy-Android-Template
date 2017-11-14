package dz.crystalbox.groovyandroidtemplate

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.error.VolleyError
import com.android.volley.request.StringRequest
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.SaveInstance
import com.orm.SugarRecord
import dz.crystalbox.groovyandroidtemplate.component.AppComponent
import dz.crystalbox.groovyandroidtemplate.component.DaggerAppComponent
import dz.crystalbox.groovyandroidtemplate.helper.Async
import dz.crystalbox.groovyandroidtemplate.model.Person
import dz.crystalbox.groovyandroidtemplate.vendor.VolleyPlus
import groovy.transform.CompileStatic

import javax.inject.Inject

import static dz.crystalbox.groovyandroidtemplate.helper.ContextMethods.*

@CompileStatic
class MainActivity extends AppCompatActivity {

    int NOTFICATION_ID = 1

    //private @Lazy Bitmap cachedBitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
    
    //<SwissKnife>

    @InjectView(R.id.txtView)
    TextView txtView

    @InjectView(R.id.btnRequest)
    Button btnRequest

    @InjectView(R.id.btnDelete)
    Button btnDelete

    @SaveInstance
    private Person person

    // </SwissKnife>

    //<Dagger2>
    @Inject
    public SugarRecord sugar

    // more modules...

    // </Dagger2>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SwissKnife.inject(this)

        SwissKnife.loadExtras(this)

        SwissKnife.restoreState(this, savedInstanceState)

        // inject using Dagger2 AppComponent
        AppComponent appInjector = DaggerAppComponent.builder().build()
        appInjector.inject(this)

        // use the injected ORM (SugarORM)
        if (sugar.count(Person) == 0){
            // create using @TupleConstructor
            new Person('Ibrahim', 34).save()
        }

        def p1 = sugar.listAll(Person).first() // or Person.findById(Person, 1)
        txtView.text = p1.toString()

        log "Nbr of Person: ${sugar.count(Person)}"

        // VolleyPlus (groovy syntax)
        boolean clearCache = true
        boolean shouldCache = false
        btnRequest.onClick {
            VolleyPlus.getInstance(owner, clearCache).addToRequestQueue(
                    new StringRequest(Request.Method.GET, "https://api.github.com",
                            {String response ->
                                toast response show()
                            }, { VolleyError error -> log error.message}){
                        @Override // additional headers
                        Map getHeaders(){
                            ["content-type":"application/json", "Accept":"application/vnd.github.v3+json"]
                        }
//                        @Override // additional params
//                        Map getParams(){ [ : ] }
                    }
            , shouldCache)
        }

        btnDelete.onClick {
            alert(this){
                title = 'Delete'
                message = 'Do you want really to delete?'
                cancelable = true
                setPositiveButton('Yes', { DialogInterface dialog, int which ->
                    p1.delete()
                    log "Nbr of Person: ${sugar.count(Person)}"
                })
                setNegativeButton('No', null)
            }

        }

        if (savedInstanceState){
            log "person: ${person}"
        } else {
            log "savedInstanceState is NULL"
        }

        /*SharedPreferences prefs = getPreferences(MODE_PRIVATE)
        if (prefs.contains('data_date')){
            log prefs.getString('data_date', 'NO DATE SAVED.')
        } else {
            SharedPreferences.Editor editor = prefs.edit()
            editor.putString('data_date', new Date().format('dd-MM-yyyy hh:mm'))
//            editor.remove('data_date')
            editor.commit()
            log 'current date saved.'
        }*/
        // using SharedPreferences
        def pref = prefs(this)
        if (pref.contains('data_date')){
            log pref.getString('data_date', 'NO DATE SAVED.')
        } else {
            prefs(this) {
                putString('data_date', new Date().format('dd-MM-yyyy hh:mm'))
//                remove('data_date')
            }
        }

    } // onCreate

    @OnClick(R.id.btnNotif)
    public sendNotif(View v){
        notify(this, NOTFICATION_ID){
            smallIcon = R.drawable.notification_icon_background
//            largeIcon = cachedBitmap // lazly loaded
            contentTitle = "My Notification"
            contentText = "Hello"
//            contentIntent = pendingActivityIntent(0, intent(WelcomeActivity), 0)
            ongoing = false
        }
    }

    @OnClick(R.id.btnAsync)
    void asyncRequest(View v){
        def task = Async.background {
            // This closure runs in a background thread, all other closures run in UI thread
            Thread.sleep(1_000)
//            def json = (Map) new JsonSlurper().parse([:], new URL('https://www.bitstamp.net/api/ticker/'), 'utf-8')
            ['json':'done.'] //json.get("last")
        } first {
            // this runs before the background task in the UI thread
            txtView.text = 'Loading...'
        } then { Map result ->
            // This runs in the UI thread after the background task
//            (view as Button).enabled = false
            log "running in the UI thread: ${result}"
            txtView.setText(result['json'].toString())
        } onError { error ->
            // This runs if an error occurs in any closure
            log "ERROR! ${error.class.name} ${error.message}"
        } execute()
    }

    @OnClick(R.id.btnCrud)
    void openCrud(View v){
        startActivity( intent(CrudActivity).putExtra('api_key', new Date().format('dd-MM-yyyy')) )

    }
}
