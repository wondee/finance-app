<script>
import { Line } from "vue-chartjs";
import { toCurrency } from "../Utils";

export default {
  extends: Line,
  props: ["entries"],
  computed: {
    chartData: function() {
      const chartData = { labels: [], data: [] };
      if (!this.entries) return chartData;

      this.entries.forEach(entry => {
        chartData.data.push(entry.currentAmount);
        chartData.labels.push(entry.displayMonth);
      });

      return chartData;
    }
  },
  watch: {
    entries: function() {
      if (this._chart) {
        this._chart.destroy();
      }
      this.renderLineChart();
    }
  },
  mounted() {
    this.renderLineChart();
  },
  methods: {
    renderLineChart() {
      const { labels, data } = this.chartData;
      this.renderChart(
        {
          labels,
          datasets: [
            {
              backgroundColor: "#DDDDFF",
              borderColor: "#777777",
              borderWidth: 1,
              pointRadius: 2,
              pointHoverRadius: 5,
              fill: false,
              data
            }
          ]
        },
        {
          responsive: true,
          maintainAspectRatio: false,
          layout: {
            padding: 20
          },
          plugins: {},
          legend: {
            display: false
          },
          scales: {
            yAxes: [
              {
                ticks: {
                  fontColor: "white",
                  callback: function(label) {
                    return toCurrency(label);
                  }
                }
              }
            ],
            xAxes: [
              {
                ticks: {
                  fontColor: "white"
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
  }
};
</script>