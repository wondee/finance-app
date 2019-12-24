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
            backgroundColor: '#DDDDDD',
            borderColor: '#555555',
            borderWidth: 1, 
            pointRadius: 2,
            pointHoverRadius: 5,
            fill: false,
            data: chartData.data
          }
        ]
      },
      {
        responsive: true,
        maintainAspectRatio: false,
        layout: {
          padding: 20
        },
        plugins: {
        },
        legend: {
          display: false
        },
        scales: {
          yAxes: [
            {
              ticks: {
                fontColor: 'white',
                callback: function(label) {
                  return toCurrency(label);
                }
              }
            }
          ],
          xAxes: [
            {
              ticks: {
                fontColor: 'white'
              }
            }
          ]
        },
        tooltips: {
          enabled: true,
          mode: "single",
          callbacks: {
            label: function(tooltipItems) {
              return toCurrency(tooltipItems.yLabel);
            }
          }
        }
      }
    );
  }
};
</script>