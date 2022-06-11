package com.boudaa.tools;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gsnotes.exceptionhandlers.InscriptionException;
import com.gsnotes.web.models.Inscription;

public class ExcelImporter {
	
	public List<Inscription> excelImport(HttpSession session,InputStream inputStream){
		if(session.getAttribute("messageExcelImporter")!=null)	session.removeAttribute("messageExcelImporter");
		List<Inscription> listStudent=new ArrayList<>();
		long idEtudiant=0;
		String CNE="";
		String Nom="";
		String Prenom="";
		long idNiveau=0;
		String type="";
		String message="";
		
		try {
			Workbook workbook=new XSSFWorkbook(inputStream);
			Sheet firstSheet=workbook.getSheetAt(0);
			Iterator<Row> rowIterator=firstSheet.iterator();
			Row verification =rowIterator.next();
			Iterator<Cell> cellIt=verification.cellIterator();
			//verifier le format du fichier
		    String[] tab = {"ID ETUDIANT","CNE","NOM", "PRENOM","ID NIVEAU ACTUEL","TYPE" };
					for( int i=0; i<6 ;i++) {
						if(cellIt.hasNext()) {
						Cell cell=cellIt.next();
						String title=cell.getStringCellValue();
						// les titre ne sont pas conforme a la forme necessaire
						if(!title.equals(tab[i])){
							message=" Les colonnes du fichier ne sont pas conforme a la forme necessaire";
							session.setAttribute("messageExcelImporter", message);
						return null;

						}

						}
						//le nombre des colonnes inferieur au necessaire
						else{ message="Le nombre des colonnes n'est pas conforme a la forme necessaire";
						System.out.println(message);
						session.setAttribute("messageExcelImporter", message);
						return null;


					}
							
						}
					//le nombre des colonnes superieur au necessaire

					if(cellIt.hasNext()) {
						 message="Le nombre des colonnes n'est pas conforme a la forme necessaire";
						 session.setAttribute("messageExcelImporter", message);
							return null;


					}
					//Stocker les information dans une liste d'inscription
			while(rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator=nextRow.cellIterator();
				while(cellIterator.hasNext()) {
					Cell nextCell=cellIterator.next();
					int columnIndex=nextCell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						idEtudiant=(long)nextCell.getNumericCellValue();
						System.out.println(idEtudiant);
						break;
					case 1:
						CNE=nextCell.getStringCellValue();
						System.out.println(CNE);
						break;
					case 2:
						Nom=nextCell.getStringCellValue();
						System.out.println(Nom);
						break;
					case 3:
						Prenom=nextCell.getStringCellValue();
						System.out.println(Prenom);
				 		break;
					case 4:
						idNiveau=(long) nextCell.getNumericCellValue();
						System.out.println(idNiveau);
						break;
					case 5:
						type=nextCell.getStringCellValue();
						System.out.println(type);
						break;
					
					}
					
				}
				// verifier que toutes les informations sont rempli
				if(idEtudiant!=0 && CNE!="" && Nom!="" && Prenom !="" && type!="" && idNiveau!=0) {
				listStudent.add(new Inscription(idEtudiant, CNE,Nom, Prenom,type, idNiveau ));
				System.out.println("nouvel instance entrée");
				}
				
			}
			
			workbook.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			 message = e.getMessage();
			

		}
		
		return listStudent;
	}

}