package pers.pudgebd.antlr.visitor;


import pers.pudgebd.antlr.autogen.SqlBaseBaseVisitor;
import pers.pudgebd.antlr.autogen.SqlBaseParser;

public class ValidateGrammarVisitor extends SqlBaseBaseVisitor<Boolean> {

    @Override
    public Boolean visitSingleStatement(SqlBaseParser.SingleStatementContext ctx) {
        super.visitSingleStatement(ctx);
        return true;
    }

//    @Override
//    public Boolean visitRelationPrimary(RelationPrimaryContext ctx) {
//        RelationContext relation = ctx.relation();
//        String a = CommonSqlLvl01Utils.getOriginTextFromCtx(relation);
//        return super.visitRelationPrimary(ctx);
//    }
}
