package dz.crystalbox.groovyandroidtemplate

import dz.crystalbox.groovyandroidtemplate.model.Person
import groovy.transform.CompileStatic
import spock.lang.Specification

@CompileStatic
class MainActivitySpec extends Specification  {

    def "should calculate a total"() {
        given:
            int total; int x = 2; int y = 1
        when:
            total = x + y
        then:
            total == 3
    }
}
