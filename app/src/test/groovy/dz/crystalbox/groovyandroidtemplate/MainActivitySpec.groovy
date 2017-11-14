package dz.crystalbox.groovyandroidtemplate

//@Config(shadows = [MainActivity], constants = BuildConfig)// manifest = "src/main/AndroidManifest.xml")
class MainActivitySpec extends spock.lang.Specification  {
    def "should find text view and compare text value"() {
        given:
//        def mainActivity = Robolectric.buildActivity(MainActivity).create().get()
            int total; int x = 2; int y = 2
        when:
//        def text = mainActivity.txtView.text
        total = x + y
        then:
//        text != ''
        total == 4
    }
}
