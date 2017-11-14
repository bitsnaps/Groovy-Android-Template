package dz.crystalbox.groovyandroidtemplate.component

import com.orm.SugarRecord
import dagger.Component
import dagger.android.AndroidInjectionModule
import dz.crystalbox.groovyandroidtemplate.CrudActivity
import dz.crystalbox.groovyandroidtemplate.MainActivity
import dz.crystalbox.groovyandroidtemplate.module.SugarModule
import groovy.transform.CompileStatic

import javax.inject.Singleton

@CompileStatic
@Singleton
@Component(modules = [AndroidInjectionModule, SugarModule /*, VolleyModule*/])
interface AppComponent {

    void inject(MainActivity mainActivity)
    void inject(CrudActivity crudActivity)

    SugarRecord providesSugar()
//    VolleyPlus /*RequestQueue*/ providesVolley()

}
