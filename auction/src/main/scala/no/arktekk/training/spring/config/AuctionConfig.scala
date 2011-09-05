package no.arktekk.training.spring.config

import org.springframework.context.annotation._
import org.springframework.web.servlet.view.InternalResourceViewResolver
import org.springframework.context.support.ResourceBundleMessageSource
import javax.sql.DataSource
import no.arktekk.training.spring.repository.{LabelRepository, CategoryRepository, AlbumRepository, AuctionRepository}
import no.arktekk.training.spring.controller.{AuctionController, FrontPageController}
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.support.GenericConversionService
import org.springframework.beans.factory.annotation.Autowired
import no.arktekk.training.spring.converter.{CategoryConverter, LabelConverter}

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
@Configuration
class AuctionConfig {

  @Bean def viewResolver = new InternalResourceViewResolver {
    {
      setPrefix("/WEB-INF/views/")
      setSuffix(".jspx")
    }
  }

  @Bean def messageSource = new ResourceBundleMessageSource {
    {
      setUseCodeAsDefaultMessage(true)
      setBasename("labels")
    }
  }

  @Autowired def conversionService(conversionService: GenericConversionService) {
    conversionService.addConverter(new LabelConverter(labelRepository))
    conversionService.addConverter(new CategoryConverter(categoryRepository))
  }

  @Bean def frontPageController = new FrontPageController(auctionRepository)

  @Bean def auctionController = new AuctionController(auctionRepository, categoryRepository, labelRepository)

  @Bean def dataSource: DataSource = new TestDataSourceFactoryBean().getObject

  @Bean def categoryRepository = new CategoryRepository(dataSource)

  @Bean def labelRepository = new LabelRepository(dataSource)

  @Bean def albumRepository = new AlbumRepository(dataSource, categoryRepository, labelRepository)

  @Bean def auctionRepository = new AuctionRepository(dataSource, albumRepository)

  @Bean def testDataPopulator() = new TestDataPopulator(dataSource) {
    {
      generateDatabaseIfEmpty()
    }
  }
}