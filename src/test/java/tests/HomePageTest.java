package tests;

import java.io.IOException;

//import org.pageobjects.RecipeAtoE;
import org.pageobjects.RecipeFtoJ;
import org.pageobjects.RecipeUtoZ;
//import org.pageobjects.RecipeKtoO;
//import org.pageobjects.RecipePtoT;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.TestBase;

public class HomePageTest extends TestBase {

	//HomePage homePage;

	public HomePageTest() {
		super();

	}

	@BeforeClass
	public void setup() {

		TestBase.initialization();
	}


	@Test(priority=1)
	public void LFV_AtoERecipesTest() throws Exception{
		
		LFV_AtoE_Recipes recObj = new LFV_AtoE_Recipes(TestBase.getDriver());
		
		recObj.read_LFV_Elimination_Excel();
		
		recObj.read_CuisineCategoryData_Excel();
		
		recObj.click_AtoZ_recipes();
		
		recObj.getRecipeInfo();
	}
	
	@Test(priority=2)
	public void LCHF_AtoERecipesTest() throws Exception {
		
		LCHF_AtoE_Recipes recObj = new LCHF_AtoE_Recipes(TestBase.getDriver());
		
		recObj.read_CuisineCategoryData_Excel();
		
		recObj.click_AtoZ_recipes();
		
		recObj.getRecipeInfo();
	}
	
	
	@Test(priority = 3)
	public void LFV_Allergy_Milk_Test() throws Exception{
		
		LFV_Allergy_Milk recObj = new LFV_Allergy_Milk(TestBase.getDriver());
		
		recObj.read_CuisineCategoryData_Excel();
		
		recObj.click_AtoZ_recipes();
		
		recObj.getRecipeInfo();
	}

	@Test(priority=4)
	public void recipes_FtoJ_Test() throws InterruptedException, IOException {
		
		RecipeFtoJ recipe = new RecipeFtoJ(getDriver());
		
		recipe.read_Excel();
		
		recipe.read_CuisineCategoryData_Excel();
		
		recipe.click_AtoZ_recipes();
		
		recipe.getRecipeInfo();
	}
	
	@Test(priority=5)
	public void recipes_KtoO_Test() throws InterruptedException, IOException {
		
		
	}
	
	@Test(priority=6)
	public void recipes_PtoT_Test() throws InterruptedException, IOException {
		
		Recipes_PtoT recipe = new Recipes_PtoT(getDriver());
		
		recipe.read_EliminationList_Excel();
		
		recipe.read_CuisineCategoryData_Excel();
		
		recipe.click_AtoZ_recipes();
		
		recipe.getRecipeInfo();
		
		
	}
	
	@Test(priority=7)
	public void recipes_UtoZ_Test() throws Exception {
		
		RecipeUtoZ recipe = new RecipeUtoZ(getDriver());
		
		recipe.click_AtoZ_recipes();
		
		recipe.jgetRecipeInfo();
		
	}
	
	@AfterClass
	public void teardown() {

		getDriver().quit();
	}


}
