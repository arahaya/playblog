package controllers;

import play.mvc.With;

@CRUD.For(models.Category.class)
@With(Secure.class)
public class Categories extends CRUD {

}