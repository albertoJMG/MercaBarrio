/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidades.Cliente;
import entidades.Pedido;
import entidades.SubPedido;
import entidades.Tienda;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import modelo.MercaBarrioModelo;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "grafica")
@RequestScoped
public class Grafica {

    private BarChartModel barModel;

    @PostConstruct
    public void init() {

        createBarModel();

    }

    private void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Ventas totales mensuales");

        List<Number> values = new ArrayList<>();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Tienda t = (Tienda) sessionMap.get("usuarioLogeado");
        List<SubPedido> subPedidosEnero = new LinkedList<>();
        List<SubPedido> subPedidosFebrero = new LinkedList<>();
        List<SubPedido> subPedidosMarzo = new LinkedList<>();
        List<SubPedido> subPedidosAbril = new LinkedList<>();
        List<SubPedido> subPedidosMayo = new LinkedList<>();
        List<SubPedido> subPedidosJunio = new LinkedList<>();
        List<SubPedido> subPedidosJulio = new LinkedList<>();
        List<SubPedido> subPedidosAgosto = new LinkedList<>();
        List<SubPedido> subPedidosSeptiembre = new LinkedList<>();
        List<SubPedido> subPedidosOctubre = new LinkedList<>();
        List<SubPedido> subPedidosNoviembre = new LinkedList<>();
        List<SubPedido> subPedidosDiciembre = new LinkedList<>();
        List<SubPedido> subPedidos = MercaBarrioModelo.buscarSubPedidosTienda(Long.toString(t.getId_usuario()));
        Date fechaActual = new Date();
        System.out.println(fechaActual);
        Calendar calPedidos = Calendar.getInstance();
        Calendar calActual = Calendar.getInstance();
        calActual.setTime(fechaActual);
        for (SubPedido sp : subPedidos) {
            calPedidos.setTime(sp.getPedido().getFecha_pedido());
            if (calPedidos.get(Calendar.MONTH) == 0 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 1 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 2 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 3 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 4 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 5 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 6 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 7 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 8 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosSeptiembre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 9 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 10 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
            if (calPedidos.get(Calendar.MONTH) == 11 && calPedidos.get(Calendar.YEAR) == calActual.get(Calendar.YEAR)) {
                subPedidosOctubre.add(sp);

            }
        }

        values.add(MercaBarrioUtil.ventasPorMes(subPedidosEnero));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosFebrero));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosMarzo));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosAbril));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosMayo));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosJunio));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosJulio));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosAgosto));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosSeptiembre));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosOctubre));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosNoviembre));
        values.add(MercaBarrioUtil.ventasPorMes(subPedidosDiciembre));

        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        bgColor.add("rgba(204, 4, 51, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        borderColor.add("rgb(204, 4, 51)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("Enero");
        labels.add("Febrero");
        labels.add("Marzo");
        labels.add("Abril");
        labels.add("Mayo");
        labels.add("Junio");
        labels.add("Julio");
        labels.add("Agosto");
        labels.add("Septiembre");
        labels.add("Octubre");
        labels.add("Noviembre");
        labels.add("Diciembre");
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

//        Title title = new Title();
//        title.setDisplay(true);
//        title.setText("Ventas");
//        options.setTitle(title);
//        Legend legend = new Legend();
//        legend.setDisplay(true);
//        legend.setPosition("top");
//        LegendLabel legendLabels = new LegendLabel();
//        legendLabels.setFontStyle("bold");
//        legendLabels.setFontColor("#2980B9");
//        legendLabels.setFontSize(24);
//        legend.setLabels(legendLabels);
//        options.setLegend(legend);
// 
        barModel.setOptions(options);
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

}
