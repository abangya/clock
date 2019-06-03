package ${serviceImplPackage};

import ${modelPackage}.${modelNameUpperCamel};
import ${mapperPackage}.${modelNameUpperCamel}Mapper;
import ${pageClassPath};
import ${servicePackage}.${modelNameUpperCamel}Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.annotations.Param;

/**
 *
 * Created by ${author} on ${date}.
 */
@Service
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service{

	@Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;
    
	<#list serviceMethodsList as serviceMethod>
${serviceMethod!''}
	</#list>
}
