package com.etrade.puggo.config;

import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.TXTFormat;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

/**
 * @Description 类描述
 * @Author fengquan@huice.com
 * @Date 2021-05-12
 **/
@Slf4j
public class JooqSqlLoggerListener extends DefaultExecuteListener {

    private static final JooqLogger logger = JooqLogger.getLogger(JooqSqlLoggerListener.class);

    //
    // @Override
    // public void renderEnd(ExecuteContext ctx) {
    //     printSql("renderEnd",ctx);
    // }
    //
    // @Override
    // public void prepareEnd(ExecuteContext ctx) {
    //     printSql("prepareEnd",ctx);
    // }
    //
    @Override
    public void executeEnd(ExecuteContext ctx) {
        printSql("executeEnd", ctx);
    }
    //
    // @Override
    // public void bindEnd(ExecuteContext ctx) {
    //     printSql("bindEnd", ctx);
    // }
    //
    // @Override
    // public void outEnd(ExecuteContext ctx) {
    //     printSql("outEnd", ctx);
    // }
    //
    // @Override
    // public void fetchEnd(ExecuteContext ctx) {
    //     printSql("fetchEnd", ctx);
    // }

    // @Override
    // public void end(ExecuteContext ctx) {
    //     printSql("end", ctx);
    // }

    @Override
    public void resultEnd(ExecuteContext ctx) {
        if (ctx.result() != null) {
            if (logger.isDebugEnabled()) {
                logger.info("Fetched row(s)", ctx.result().size());
            }
            if (logger.isDebugEnabled()) {
                logMultiline(ctx.result().format(TXTFormat.DEFAULT.maxRows(5).maxColWidth(50)));
            }

        }
    }

    /**
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2022/9/27 2:10 PM
     * @editTime 2022/9/27 2:10 PM
     */
    public void printSql(String source, ExecuteContext ctx) {
        if (!logger.isDebugEnabled()) {
            return;
        }

        Configuration configuration = ctx.configuration();

        String[] batchSQL = ctx.batchSQL();
        if (ctx.query() != null) {

            String inlined = DSL.using(configuration).renderInlined(ctx.query());
            if (!ctx.sql().equals(inlined)) {
                logger.info(source + " -> with bind values -> ", inlined);
            }
        } else if (ctx.routine() != null) {
            logger.info("Calling routine", ctx.sql());

            String inlined = DSL.using(configuration)
                .renderInlined(ctx.routine());

            if (!ctx.sql().equals(inlined)) {
                logger.info(source + " -> with bind values -> ", inlined);
            }
        } else if (!StringUtils.isBlank(ctx.sql())) {

            logger.info(
                String.format(source + " -> executing %s query -> ", ctx.type() == ExecuteType.BATCH ? " batch" : ""),
                ctx.sql());
        } else if (batchSQL.length > 0) {
            if (batchSQL[batchSQL.length - 1] != null) {
                for (String sql : batchSQL) {
                    logger.info(source + " -> executing batch query -> ", sql);
                }
            }
        }
    }

    private void logMultiline(String message) {

        String ct = "Fetched result";
        for (String line : message.split("\n")) {
            logger.info(ct, line);
            ct = "";
        }
    }
}

