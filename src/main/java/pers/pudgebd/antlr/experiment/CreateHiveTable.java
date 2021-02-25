package pers.pudgebd.antlr.experiment;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;
import pers.pudgebd.antlr.autogen.SqlBaseLexer;
import pers.pudgebd.antlr.autogen.SqlBaseParser;
import pers.pudgebd.antlr.util.visitor.ParseErrorListener;
import pers.pudgebd.antlr.util.visitor.UpperCaseCharStream;
import pers.pudgebd.antlr.visitor.ValidateGrammarVisitor;

public class CreateHiveTable {

    public static void main(String[] args) {
        String sql = "CREATE TABLE `default`.FlinkWriteHiveTable (\n" +
                "    order_type bigint COMMENT '订单类型, 0:订单；1：撤单',\n" +
                "    acct_id string COMMENT '投资者账户代码',\n" +
                "    order_no bigint COMMENT '原始订单参考编号',\n" +
                "    sec_code string comment '产品代码',\n" +
                "    trade_dir string COMMENT '交易方向,B 或者 S',\n" +
                "    order_price bigint comment '交易价格，单位为分',\n" +
                "    order_vol bigint comment '含3位小数，比如数量为100股，则交易数量为二进制100000',\n" +
                "    act_no bigint COMMENT '订单确认顺序号',\n" +
                "    withdraw_order_no bigint COMMENT '撤单订单编号',\n" +
                "    pbu double COMMENT '发出此订单的报盘机编号',\n" +
                "    order_status string COMMENT '订单状态,0=New,1=Cancelled,2=Reject',\n" +
                "    ts bigint COMMENT '订单接收时间，微妙级时间戳',\n" +
                "    score double\n" +
                ") PARTITIONED BY (`dt` String, `hour` string, `minute` string) STORED AS parquet TBLPROPERTIES (\n" +
                "  'connector'='filesystem',\n" +
                "  'path'='hdfs://cdh601:8020/user/hive/warehouse/flinkwritehivetable',\n" +
                "  'format'='parquet',\n" +
                "  'partition.time-extractor.timestamp-pattern'='$dt $hour:$minute:00',\n" +
                "  'sink.partition-commit.trigger'='partition-time',\n" +
                "  'sink.partition-commit.delay'='1 minute',\n" +
                "  'sink.partition-commit.policy.kind'='metastore,success-file'\n" +
                ")";
        UpperCaseCharStream charStream = new UpperCaseCharStream(CharStreams.fromString(sql));
        SqlBaseLexer lexer = new SqlBaseLexer(charStream);
        lexer.removeErrorListeners(); // 去除控制台打印错误
        lexer.addErrorListener(new ParseErrorListener());

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SqlBaseParser parser = new SqlBaseParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new ParseErrorListener());
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);

        ValidateGrammarVisitor visitor = new ValidateGrammarVisitor();
        Boolean success = visitor.visit(parser.singleStatement());
        System.out.println(success);
    }

}
