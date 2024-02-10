import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.service.ProductBaseService
import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.http.client.HttpClient
import spock.lang.Shared
import spock.lang.Specification


@SuppressWarnings(['JUnitPublicNonTestMethod', 'JUnitPublicProperty'])
@Integration
class ProductBaseControllerIntSpec extends Specification {

    @Shared HttpClient client

    ProductBaseService productBaseService

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        this.client  = HttpClient.create(baseUrl.toURL())
    }

    def "save one ProductItem"(){
        given:
        List<Serializable> ids = []
        ProductBase.withNewTransaction {
            ids << productBaseService.save(new ProductBase(name: 'sitrom', description: '20 pz'))
        }

        expect:
        productBaseService.count() == 1

        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        controller.save()

        then:
        resp.STATUS_IN_STOCK == HttpStatus.OK
        resp.body()


    }

}
