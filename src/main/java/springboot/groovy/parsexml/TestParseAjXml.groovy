package springboot.groovy.parsexml

import groovy.xml.MarkupBuilder
import java.util.List
import java.util.Map

class TestParseAjXml {

	static void main(String... args) {
		makeXml()
	}

	static def List<Map<String, String>> parseXml(String xml) {
		List<Map<String, String>> tableList = new ArrayList<>();
		def root = new XmlParser().parseText(xml)
		println root.name()
		println root.attributes()
		List tables = root.children()
		tables.each{ table ->
			Map<String, Object> tableName = new HashMap<>();
			List r = table.children()
			Map<String, String> colNameAndValue = new HashMap<>();
			List<Map<String, String>> tablesList = new ArrayList<>();
			r.forEach { cols ->
				if(cols.name().equals("R")) {
					cols.each { col ->
						colNameAndValue.put(col.name(), col.text().trim())
						//println "表名：" + table.name() +"  字段名："+ col.name() + " 值：" + col.text()
					}
				}else {
					colNameAndValue.put(cols.name(), cols.text().trim())
					//println "表名：" + table.name() +"  字段名："+ cols.name() + " 值：" + cols.text()
				}
				tablesList.add(colNameAndValue);
			}
			tableName.put(table.name(), tablesList)
			tableList.add(tableName)
		}
		return tableList
	}

	static def String makeXml() {
		def strXml = new StringWriter()
		MarkupBuilder mb  = new groovy.xml.MarkupBuilder(strXml);

		mb.'?xml'(version:"1.0", encoding:"UTF-8")
		mb.interview{
			data{
				person(id:"05891", comments:"social recruitment"){
					title{
						position(code:"P7","Staff SE")
					}
					age("29")
					assessment(interviewer:"manager1", "tech is ok")
					experience{
						phase(from:"2012", to:"now", "CompanyA")
						phase(from:"2010", to:"2012", "CompanyB")
					}
				}

				person(id:"05892", comments:"campus recruitment"){
					title{
						position(code:"P6","SE")
					}
					age("25")
					assessment(interviewer:"manager2", "tech is ok")
					experience{
						phase(from:"2012", to:"now", "UniversityA")
						phase(from:"2011", to:"2012", "CompanyB")
					}
				}
			}
			interviewInfo{
				date("2016-10-12")
				address("meetingroom 402")
				organizator(dept:"cloud","ZhangSan")
			}
		}

		print strXml

//		def xmlFile = "output.xml"
//		PrintWriter pw = new PrintWriter(xmlFile)
//		pw.write(strXml.toString())
//		pw.close()
	}
}
