package dz.crystalbox.groovyandroidtemplate.module

import com.orm.SugarRecord
import dagger.Module
import dagger.Provides
import groovy.transform.CompileStatic
import javax.inject.Singleton

@CompileStatic
@Module
class SugarModule {

    @Singleton // singleton instance
    @Provides
    SugarRecord providesSugar(){
        new SugarRecord()
    }

}
