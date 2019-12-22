<script>
import { Line } from "vue-chartjs";
import { toCurrency } from "./Utils";

export default {
  extends: Line,
  props: ["entries"],
  mounted() {
    const chartData = { labels: [], data: [] };

    this.entries.forEach(entry => {
      chartData.data.push(entry.currentAmount);
      chartData.labels.push(entry.displayMonth);
    });

    this.renderChart(
      {
        labels: chartData.labels,
        datasets: [
          {
            backgroundColor: "rgb(54, 162, 235)",
            pointRadius: 1,
            fill: false,
            data: chartData.data
          }
        ]
      },
      {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        scales: {
          yAxes: [
            {
              ticks: {
                callback: function(label, index, labels) {
                  return toCurrency(label);
                }
              }
            }
          ]
        },
        tooltips: {
          enabled: true,
          mode: "single",
          callbacks: {
            label: function(tooltipItems, data) {
              return toCurrency(tooltipItems.yLabel);
            }
          }
        }
      }
    );
  }
};
</script>