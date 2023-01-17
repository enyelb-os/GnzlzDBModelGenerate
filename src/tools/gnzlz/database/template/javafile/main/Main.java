package tools.gnzlz.database.template.javafile.main;

import tools.gnzlz.command.GroupCommand;
import tools.gnzlz.filetemplete.ListTemplates;
import tools.gnzlz.filetemplete.TypeTemplate;
import tools.gnzlz.filetemplete.Console;
import tools.gnzlz.template.template.Template;

public class Main {

    public static void main(String[] args) {
        Template.path = "/tools/gnzlz/database/template/javafile/";
        ListTemplates.isObjectsDBModel = true;

        ListTemplates.file("configuration","model/DataBase.jv", TypeTemplate.CATALOG);
        ListTemplates.file("model_base","model/DBModelBase.jv", TypeTemplate.MODEL);
        ListTemplates.file("model_custom","model/DBModelCustom.jv", TypeTemplate.MODEL);
        ListTemplates.file("model","model/DBModel.jv", TypeTemplate.MODEL);

        GroupCommand.command("package").value("db").commands("--package", "-pkg");
        GroupCommand.command("create", args, () -> {
            GroupCommand.command("model", args, () -> {
                GroupCommand.command("model_name").required("Enter name model").commands("--model", "-nm");
                Console.processModel(args,"model_name", "model_base", "model_custom", "model");
            });
            GroupCommand.command("configuration", args, () -> {
                Console.processModel(args,"", "configuration");
            });

        });
        GroupCommand.command(args, () -> {
            Console.process(args);
        });


    }
}
