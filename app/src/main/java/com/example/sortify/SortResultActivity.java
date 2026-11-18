package com.example.sortify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.sortify.R;
import com.example.sortify.utils.PreferencesManager;
import java.util.HashMap;
import java.util.Map;

public class SortResultActivity extends AppCompatActivity {

    private TextView textViewItemName, textViewCategory, textViewInstructions, textViewImpact;
    private Button buttonBack, buttonSortAnother, buttonGoHome;
    private PreferencesManager preferencesManager;
    private Map<String, WasteInfo> wasteDatabase = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_result);

        preferencesManager = new PreferencesManager(this);
        initializeWasteDatabase();
        initializeViews();

        String itemName = getIntent().getStringExtra("item_name");
        if (itemName != null) {
            displayResult(itemName);
            String category = textViewCategory.getText().toString();
            preferencesManager.awardPoints(10, itemName, category);
            Toast.makeText(this, "+10 points earned!", Toast.LENGTH_SHORT).show();
        }

        setupButtonListeners();
    }

    private void initializeWasteDatabase() {
        wasteDatabase = new HashMap<>();

        // ===================== ORGANIC/COMPOST (15) =====================
        wasteDatabase.put("apple", new WasteInfo("Organic Waste / Compostable", "🍎",
                "🍎 Food waste should be composted!\n\n✅ Best: Add to compost bin\n✅ Alternative: Bury in garden\n❌ Avoid sending to landfill",
                "Food waste in landfills produces methane, a greenhouse gas 25x more potent than CO2."));

        wasteDatabase.put("banana", new WasteInfo("Organic Waste / Compostable", "🍌",
                "🍌 Peel is highly compostable!\n\n✅ Remove peel and compost\n✅ Great for garden soil\n✅ Attracts beneficial insects",
                "Banana peels contain potassium and nitrogen - perfect for soil enrichment."));

        wasteDatabase.put("orange peel", new WasteInfo("Organic Waste / Compostable", "🍊",
                "🍊 Citrus peels break down quickly!\n\n✅ Add to compost\n✅ Use for homemade cleaner\n✅ Dry for aromatic use",
                "Citrus compost improves soil structure and deters pests naturally."));

        wasteDatabase.put("vegetable scraps", new WasteInfo("Organic Waste / Compostable", "🥕",
                "🥬 All vegetable waste is compostable!\n\n✅ Leafy greens\n✅ Root ends\n✅ Spoiled vegetables",
                "Composting vegetable scraps returns nutrients to soil, reducing need for fertilizers."));

        wasteDatabase.put("meat", new WasteInfo("Organic Waste (Advanced Compost)", "🥩",
                "🥩 Meat requires special handling!\n\n✅ Hot compost only (70°C+)\n✅ Bury deeply in garden\n❌ NOT for home composting beginners",
                "Meat attracts animals. Only compost in hot bins or bury 30cm+ deep."));

        wasteDatabase.put("bones", new WasteInfo("Organic Waste (Slow Decompose)", "🦴",
                "🦴 Bones are slow to decompose!\n\n✅ Bury deep in garden\n✅ Crush for faster breakdown\n✅ Adds calcium to soil",
                "Bones provide slow-release calcium and phosphorus for long-term soil health."));

        wasteDatabase.put("eggshells", new WasteInfo("Organic Waste / Compostable", "🥚",
                "🥚 Eggshells are valuable compost!\n\n✅ Crush into pieces\n✅ Add to compost bin\n✅ Or directly to soil",
                "Eggshells are 95% calcium carbonate - excellent for soil amendment."));

        wasteDatabase.put("coffee grounds", new WasteInfo("Organic Waste / Compostable", "☕",
                "☕ Coffee grounds improve soil!\n\n✅ Add to compost bin\n✅ Sprinkle on garden\n✅ Use as plant fertilizer",
                "Coffee grounds add nitrogen and improve soil water retention."));

        wasteDatabase.put("tea leaves", new WasteInfo("Organic Waste / Compostable", "🍵",
                "🍵 Tea leaves are compostable!\n\n✅ Remove tea bags (non-compostable)\n✅ Loose leaves to compost\n✅ Or bury in soil",
                "Tea leaves add nutrients and tannins that improve soil quality."));

        wasteDatabase.put("grass clippings", new WasteInfo("Organic Waste / Garden Waste", "🌾",
                "🌾 Grass is valuable mulch!\n\n✅ Leave on lawn (nitrogen returns)\n✅ Layer in compost\n✅ Dry for storage",
                "Grass clippings return nitrogen to soil, reducing fertilizer needs by 25%."));

        wasteDatabase.put("leaves", new WasteInfo("Organic Waste / Garden Waste", "🍂",
                "🍂 Leaves are brown compost gold!\n\n✅ Layer in compost\n✅ Shred for faster breakdown\n✅ Use as mulch",
                "Fall leaves provide carbon-rich material and natural mulch for winter protection."));

        wasteDatabase.put("plant trimmings", new WasteInfo("Organic Waste / Compostable", "🌿",
                "🌿 Plant waste is fully compostable!\n\n✅ Diseased plants - burn or special disposal\n✅ Healthy plants - compost\n✅ Remove soil first",
                "Plant trimmings reduce garden waste volume by 30% through composting."));

        wasteDatabase.put("wood chips", new WasteInfo("Organic Waste / Mulch", "🪵",
                "🪵 Wood chips are excellent mulch!\n\n✅ Use as garden mulch\n✅ Layer in compost\n✅ Avoid treated wood",
                "Wood mulch prevents weed growth, retains moisture, and slowly enriches soil."));

        wasteDatabase.put("straw", new WasteInfo("Organic Waste / Bedding Material", "🌾",
                "🌾 Straw decomposes readily!\n\n✅ Compost for gardens\n✅ Mulch for landscaping\n✅ Animal bedding",
                "Straw provides bedding, mulch, and long-term soil carbon enrichment."));

        wasteDatabase.put("hay", new WasteInfo("Organic Waste / Garden Mulch", "🌾",
                "🌾 Hay contains seeds - use with care!\n\n✅ Compost at high temperature\n✅ Best for mulch (kills weed seeds)\n✅ Or animal bedding",
                "Hay mulch suppresses weeds and moderates soil temperature."));

        // ===================== PAPER/CARDBOARD (15) =====================
        wasteDatabase.put("paper", new WasteInfo("Recyclable Paper", "📄",
                "📄 Clean, dry paper can be recycled!\n\n✅ Remove plastic windows\n✅ No food residue\n✅ Flatten for space",
                "Recycling 1 ton of paper saves 17 trees and 7,000 gallons of water."));

        wasteDatabase.put("cardboard", new WasteInfo("Recyclable Cardboard", "📦",
                "📦 Cardboard is highly recyclable!\n\n✅ Flatten boxes\n✅ Remove tape and staples\n✅ Break down if wet",
                "Cardboard recycling uses 75% less energy than producing new cardboard."));

        wasteDatabase.put("newspaper", new WasteInfo("Recyclable Paper", "📰",
                "📰 Newspaper is 100% recyclable!\n\n✅ Bundle and secure\n✅ Keep dry\n✅ Separate from wet waste",
                "Recycled newspaper becomes tissue, packaging, and new newsprint."));

        wasteDatabase.put("magazine", new WasteInfo("Recyclable Paper", "📓",
                "📓 Magazines are recyclable!\n\n✅ Glossy pages: recyclable\n✅ Keep dry\n✅ Remove plastic covers",
                "Glossy magazine paper is fully recyclable into new paper products."));

        wasteDatabase.put("junk mail", new WasteInfo("Recyclable Paper", "💌",
                "💌 Junk mail is recyclable!\n\n✅ Remove plastic windows\n✅ Remove address labels\n✅ Recycle with paper",
                "Recycling junk mail saves space in landfills and conserves resources."));

        wasteDatabase.put("envelope", new WasteInfo("Recyclable Paper", "✉️",
                "✉️ Envelopes are recyclable!\n\n✅ Remove windows (plastic)\n✅ Remove stickers\n✅ Recycle paper part",
                "Envelope paper is recyclable; only plastic windows need removal."));

        wasteDatabase.put("cereal box", new WasteInfo("Recyclable Cardboard", "📦",
                "📦 Cereal boxes are recyclable!\n\n✅ Empty completely\n✅ Flatten box\n✅ Recycle as cardboard",
                "Food-grade cardboard is successfully recycled into new packaging."));

        wasteDatabase.put("toilet paper roll", new WasteInfo("Recyclable Paper", "📄",
                "📄 Cardboard rolls are recyclable!\n\n✅ Empty completely\n✅ Compost if clean\n✅ Recycle with paper",
                "Paper rolls are too small for most facilities; best composted."));

        wasteDatabase.put("paper bag", new WasteInfo("Recyclable Paper", "🛍️",
                "🛍️ Paper bags are recyclable!\n\n✅ Remove plastic coating\n✅ Flatten\n✅ Recycle or compost if clean",
                "Paper bags compost in 2-6 weeks and are fully recyclable."));

        wasteDatabase.put("pizza box", new WasteInfo("Recyclable Cardboard (with caution)", "📦",
                "📦 Pizza boxes: greasy? Handle carefully!\n\n✅ If clean inside: recycle\n✅ If greasy: compost\n❌ Never mix with contamination",
                "Grease contaminates recycling. Separate clean and soiled portions."));

        wasteDatabase.put("egg carton", new WasteInfo("Recyclable Paper", "🥚",
                "🥚 Egg cartons are paper-based!\n\n✅ Recycle with cardboard\n✅ Or compost if paper-only\n✅ Shred for animal bedding",
                "Paper egg cartons break down readily in composting."));

        wasteDatabase.put("paper towel", new WasteInfo("Compostable / Recyclable", "🧻",
                "🧻 Paper towels are compostable!\n\n✅ Clean ones: compost\n✅ Soiled ones: trash\n❌ Don't recycle if wet/greasy",
                "Undyed paper towels compost in weeks and enrich soil."));

        wasteDatabase.put("tissue paper", new WasteInfo("Compostable Paper", "🧻",
                "🧻 Tissues are compostable!\n\n✅ Add to compost\n✅ Mix with brown materials\n✅ Breaks down quickly",
                "Tissue paper composts faster than regular paper due to processing."));

        wasteDatabase.put("shredded paper", new WasteInfo("Recyclable / Compostable", "📄",
                "📄 Shredded paper: two options!\n\n✅ Option 1: Compost (mix with browns)\n✅ Option 2: Recycle if clean\n❌ Avoid contamination",
                "Shredded paper is excellent mulch material and breaks down quickly."));

        wasteDatabase.put("paperboard", new WasteInfo("Recyclable Cardboard", "📦",
                "📦 Paperboard is recyclable!\n\n✅ Flatten\n✅ Remove plastic linings\n✅ Bundle for collection",
                "Paperboard recycling is a major industry; easy to process."));

        // ===================== PLASTIC (20) =====================
        wasteDatabase.put("plastic bottle", new WasteInfo("Recyclable Plastic (PET #1)", "🍾",
                "♻️ Plastic bottles are highly recyclable!\n\n✅ Rinse thoroughly\n✅ Crush to save space\n✅ Remove caps\n✅ Keep labels",
                "Plastic takes 450+ years to decompose. Recycling reduces ocean pollution."));

        wasteDatabase.put("plastic bag", new WasteInfo("Non-Recyclable (Machine Hazard)", "🛍️",
                "🛍️ NEVER put bags in recycling!\n\n❌ Jams sorting machinery\n✅ Reuse for trash/storage\n✅ Use for pet waste",
                "Plastic bags wrap around recycling machinery, causing costly shutdowns."));

        wasteDatabase.put("plastic container", new WasteInfo("Recyclable Plastic (HDPE #2)", "🥫",
                "🥫 Plastic containers are recyclable!\n\n✅ Rinse food residue\n✅ Stack to save space\n✅ Remove labels\n✅ Keep lids",
                "Recycled plastic containers become new bottles, lumber, and clothing."));

        wasteDatabase.put("plastic cup", new WasteInfo("Recyclable Plastic (PET or PP #5)", "☕",
                "☕ Plastic cups are recyclable!\n\n✅ Rinse out\n✅ Remove stickers\n✅ Stack for pickup\n⚠️ Check #1 or #5 recycling symbol",
                "Recycled plastic cups become fiber fill, new cups, and containers."));

        wasteDatabase.put("food wrapper", new WasteInfo("Non-Recyclable (Contaminated)", "📦",
                "📦 Food wrappers are contaminated!\n\n❌ NOT recyclable (food residue)\n✅ Compost if paper-based\n✅ Trash otherwise",
                "Food contamination makes plastic non-recyclable and jams machinery."));

        wasteDatabase.put("plastic wrap", new WasteInfo("Non-Recyclable (Machine Hazard)", "🎁",
                "🎁 Plastic wrap jams machinery!\n\n❌ NEVER in recycling bin\n✅ Reuse for storage\n✅ Trash when damaged",
                "Plastic wrap tangles in sorting equipment, causing expensive damage."));

        wasteDatabase.put("plastic straw", new WasteInfo("Non-Recyclable (Small Item)", "🥤",
                "🥤 Plastic straws jam machines!\n\n❌ NOT recyclable (too small)\n✅ Use reusable metal/bamboo\n✅ Recycle as group",
                "Plastic straws are #1 ocean pollutant. Switch to reusable alternatives."));

        wasteDatabase.put("plastic spoon", new WasteInfo("Non-Recyclable (Small/Mixed)", "🥄",
                "🥄 Plastic utensils are problematic!\n\n❌ Too small for sorting\n✅ Use reusable utensils\n✅ Trash if single-use",
                "Single-use plastics represent 40% of all plastic waste globally."));

        wasteDatabase.put("plastic bottle cap", new WasteInfo("Recyclable (Separately)", "🧢",
                "🧢 Caps ARE recyclable now!\n\n✅ Modern machines handle caps\n✅ Leave on bottles\n✅ Recycle together",
                "Recycling caps prevents small plastic from entering oceans."));

        wasteDatabase.put("plastic milk jug", new WasteInfo("Recyclable Plastic (HDPE #2)", "🥛",
                "🥛 Milk jugs are recyclable!\n\n✅ Rinse thoroughly\n✅ Crush to save space\n✅ Keep cap on\n✅ Separate labels",
                "Recycled milk jugs become detergent bottles, lumber, and playground equipment."));

        wasteDatabase.put("plastic cling film", new WasteInfo("Non-Recyclable (Tangles)", "🎬",
                "🎬 Cling film tangles machinery!\n\n❌ NOT recyclable\n✅ Reuse or compost if plant-based\n✅ Trash otherwise",
                "Cling film causes major problems at recycling facilities."));

        wasteDatabase.put("styrofoam", new WasteInfo("Recyclable (Special Facility)", "📦",
                "📦 Styrofoam is recyclable!\n\n⚠️ Requires special facility\n✅ Check local options\n❌ NOT in regular recycling",
                "Styrofoam recycling reduces landfill waste significantly when available."));

        wasteDatabase.put("plastic tubing", new WasteInfo("Recyclable (Check Type)", "#️⃣",
                "#️⃣ Plastic tubing depends on type!\n\n✅ Check recycling number\n✅ If #1, #2, #5: recyclable\n❌ If #3, #6, #7: special facility",
                "Most plastic tubing is recyclable but requires proper categorization."));

        wasteDatabase.put("plastic box", new WasteInfo("Recyclable Plastic (HDPE #2)", "📦",
                "📦 Plastic boxes are recyclable!\n\n✅ Rinse if contaminated\n✅ Remove labels\n✅ Stack flat\n✅ Keep as is if clean",
                "Recycled plastic boxes become new containers, car parts, and outdoor furniture."));

        wasteDatabase.put("plastic sheet", new WasteInfo("Recyclable (Check Number)", "📄",
                "📄 Plastic sheets are recyclable!\n\n✅ Check recycling number\n✅ Clean thoroughly\n✅ Cut if very large",
                "Clear plastic sheets are widely recycled into new packaging."));

        wasteDatabase.put("plastic film", new WasteInfo("Recyclable (Agricultural)", "🌾",
                "🌾 Agricultural plastic film!\n\n⚠️ Special agricultural recycling\n❌ NOT in regular bins\n✅ Contact farms/suppliers",
                "Agricultural plastics require specialized recycling programs."));

        wasteDatabase.put("plastic bucket", new WasteInfo("Recyclable Plastic (HDPE #2)", "🪣",
                "🪣 Plastic buckets are recyclable!\n\n✅ Empty completely\n✅ Rinse if dirty\n✅ Remove label\n✅ Drop off at facility",
                "Buckets become new containers, outdoor furniture, and industrial products."));

        wasteDatabase.put("plastic hose", new WasteInfo("Non-Recyclable (Equipment)", "💧",
                "💧 Garden hoses are specialized!\n\n❌ NOT standard recycling\n✅ Donate if usable\n✅ Check specialized programs",
                "Some hoses can be recycled; check local hazardous waste programs."));

        wasteDatabase.put("plastic toy", new WasteInfo("Recyclable (Check Type)", "🧸",
                "🧸 Plastic toys: check material!\n\n✅ If solid plastic: recycle\n✅ If mixed materials: harder\n✅ Electronics: special handling",
                "Recycled plastic toys become new toys, planters, and park equipment."));

        // ===================== GLASS (10) =====================
        wasteDatabase.put("glass bottle", new WasteInfo("Recyclable Glass", "🥃",
                "🥃 Glass is 100% recyclable infinitely!\n\n✅ Rinse thoroughly\n✅ Remove metal caps\n✅ Keep labels\n✅ No need to crush",
                "Recycled glass reduces energy use by 30% compared to new glass production."));

        wasteDatabase.put("glass jar", new WasteInfo("Recyclable Glass", "🫙",
                "🫙 Glass jars are valuable!\n\n✅ Rinse completely\n✅ Remove lids\n✅ Reuse if possible\n✅ Recycle when worn",
                "Glass jars can be reused 10+ times before recycling."));

        wasteDatabase.put("wine bottle", new WasteInfo("Recyclable Glass", "🍾",
                "🍾 Wine bottles are premium glass!\n\n✅ Rinse and drain\n✅ Keep labels\n✅ Remove cork/cap\n✅ Special color sorting",
                "Wine bottles are recycled at higher rates due to material quality."));

        wasteDatabase.put("beer bottle", new WasteInfo("Recyclable Glass", "🍺",
                "🍺 Beer bottles have deposit value!\n\n✅ Return for deposit if applicable\n✅ Rinse and sort by color\n✅ Separate clear/brown",
                "Returnable bottles create incentive for recycling in some regions."));

        wasteDatabase.put("glass cup", new WasteInfo("Recyclable Glass", "☕",
                "☕ Glass cups are recyclable!\n\n✅ Rinse thoroughly\n✅ Remove handle if broken\n✅ No hot glass in recycling",
                "Recycled glass cups become new glass, sandpaper, and aggregate."));

        wasteDatabase.put("drinking glass", new WasteInfo("Recyclable (Tempered Glass)", "🥤",
                "🥤 Drinking glasses are recyclable!\n\n⚠️ Tempered glass may separate\n✅ Recycle with caution\n✅ Or wrap for trash",
                "Tempered glass has different melting point; separate if possible."));

        wasteDatabase.put("glass plate", new WasteInfo("Recyclable Glass", "📀",
                "📀 Glass plates are recyclable!\n\n✅ Wrap for safety\n✅ Place in glass bin\n✅ Notify facility of sharp items",
                "Recycled glass plates become new containers and decorative items."));

        wasteDatabase.put("window glass", new WasteInfo("Recyclable (Tempered/Treated)", "🪟",
                "🪟 Window glass needs care!\n\n⚠️ May be tempered or coated\n⚠️ Contains lead in old windows\n✅ Contact hazmat for old frames",
                "Old window glass often contains lead; requires special handling."));

        wasteDatabase.put("mirror", new WasteInfo("Recyclable (Backing Material)", "🪞",
                "🪞 Mirrors have reflective backing!\n\n⚠️ Backing complicates recycling\n✅ Check local programs\n✅ May need special handling",
                "Mirror backing may require removal before recycling."));

        wasteDatabase.put("glass food container", new WasteInfo("Recyclable Glass", "🥫",
                "🥫 Glass food containers are great!\n\n✅ Rinse completely\n✅ Remove plastic lids\n✅ Reuse if no damage\n✅ Recycle when ready",
                "Glass containers are infinitely recyclable without quality loss."));

        // ===================== METAL/ALUMINUM (15) =====================
        wasteDatabase.put("aluminum can", new WasteInfo("Valuable Recyclable Metal", "🥫",
                "🥫 Aluminum cans are valuable!\n\n✅ Rinse and crush\n✅ High recycling value\n✅ Sort from other metals\n✅ Keep dry",
                "Aluminum recycling saves 95% energy vs. new production. Worth money!"));

        wasteDatabase.put("aluminum foil", new WasteInfo("Recyclable Metal (Balled)", "🧿",
                "🧿 Aluminum foil is recyclable!\n\n✅ Crumple into ball (tennis ball size)\n✅ Keep separate from paper\n✅ Clean foil only",
                "Balled aluminum foil makes it detectable in sorting machines."));

        wasteDatabase.put("tin can", new WasteInfo("Recyclable Metal (Steel)", "🥫",
                "🥫 Tin cans are steel (magnetic)!\n\n✅ Rinse thoroughly\n✅ Remove labels\n✅ Crush to save space\n✅ Remove lids",
                "Steel cans are magnetically separated and recycled into new containers."));

        wasteDatabase.put("steel can", new WasteInfo("Recyclable Metal (Magnetic)", "🥫",
                "🥫 Steel cans are magnetic!\n\n✅ Rinse and drain\n✅ Crush flat\n✅ Easy to separate magnetically\n✅ High recycling rate",
                "Steel recycling preserves 85% of the energy needed for new steel."));

        wasteDatabase.put("metal lid", new WasteInfo("Recyclable Metal (Small)", "🧿",
                "🧿 Metal lids are recyclable!\n\n✅ Rinse and dry\n✅ Tape edges if sharp\n✅ Separate from plastic\n✅ Small but valuable",
                "Metal lids are sorted and recycled into new metal products."));

        wasteDatabase.put("wire", new WasteInfo("Recyclable Metal (Copper/Steel)", "🧵",
                "🧵 Metal wire is valuable!\n\n✅ Separate copper from other metals\n✅ Copper wiring pays cash\n✅ Remove plastic insulation",
                "Copper wire has high scrap value; separate from trash."));

        wasteDatabase.put("copper pipe", new WasteInfo("Valuable Recyclable Metal", "🧬",
                "🧬 Copper pipes are valuable!\n\n✅ Clean before recycling\n✅ Remove fittings\n✅ Separate from other metals\n✅ Scrap value",
                "Copper recycling is completely loss-less; infinitely recyclable."));

        wasteDatabase.put("aluminum foil container", new WasteInfo("Recyclable Metal", "📦",
                "📦 Aluminum food containers!\n\n✅ Rinse thoroughly\n✅ Nest to save space\n✅ Remove plastic lids\n✅ Recycle as aluminum",
                "Takeout containers are recycled into new food-grade containers."));

        wasteDatabase.put("aluminum pie tin", new WasteInfo("Recyclable Metal", "🥧",
                "🥧 Aluminum bakeware is recyclable!\n\n✅ Rinse and clean\n✅ Remove paper liners\n✅ Nest for shipping\n✅ Great recycling value",
                "Recycled aluminum products are used in automotive and packaging industries."));

        wasteDatabase.put("metal furniture", new WasteInfo("Recyclable Metal (Bulk)", "🪑",
                "🪑 Metal furniture is valuable!\n\n✅ Donate if reusable\n✅ Scrap metal facility if broken\n✅ Remove cushions/padding",
                "Metal furniture recycling supports sustainable furnishing industries."));

        wasteDatabase.put("metal frame", new WasteInfo("Recyclable Metal", "🖼️",
                "🖼️ Metal picture frames!\n\n✅ Remove glass and backing\n✅ Separate metals by type\n✅ Aluminum vs. steel\n✅ Scrap value",
                "Metal frames are melted down for new picture frames and products."));

        wasteDatabase.put("metal desk", new WasteInfo("Recyclable Metal (Bulk)", "🖥️",
                "🖥️ Metal office furniture!\n\n✅ Donate if usable\n✅ Scrap yard if damaged\n✅ Remove drawers and locks\n✅ Separate by metal type",
                "Metal furniture recycling keeps toxic coatings out of landfills."));

        wasteDatabase.put("metal grill grate", new WasteInfo("Recyclable Metal (Heavy)", "🍖",
                "🍖 Grill grates are steel/iron!\n\n✅ Clean off food residue\n✅ Scrap metal facility\n✅ High weight value\n✅ Separate from aluminum",
                "Steel grates are recycled into new steel products repeatedly."));

        wasteDatabase.put("metal fasteners", new WasteInfo("Recyclable Metal (Small)", "🔩",
                "🔩 Nuts, bolts, screws!\n\n✅ Collect in container\n✅ Separate ferrous from non-ferrous\n✅ Scrap metal facility\n✅ Valuable material",
                "Metal fasteners are recovered and re-manufactured into new products."));

        wasteDatabase.put("metal hanger", new WasteInfo("Recyclable Metal (Wire/Steel)", "👔",
                "👔 Clothes hangers are recyclable!\n\n✅ Wooden hangers: compost/trash\n✅ Metal hangers: recycling\n✅ Donate if reusable",
                "Metal hangers are melted down for new products repeatedly."));

        // ===================== ELECTRONIC WASTE (15) =====================
        wasteDatabase.put("battery", new WasteInfo("Hazardous Electronic Waste", "🔋",
                "🔋 Batteries contain toxic materials!\n\n❌ NEVER in trash or recycling\n✅ Take to e-waste facility\n✅ Cover terminals with tape",
                "Proper battery disposal prevents mercury and lithium contamination of water."));

        wasteDatabase.put("phone", new WasteInfo("Hazardous Electronic Waste", "📱",
                "📱 Phones contain valuable metals!\n\n✅ Take to e-waste facility\n✅ Erase data first\n✅ Donate if working\n✅ Get trade-in value",
                "One million phones contain 35 tons of silver and 350 tons of gold."));

        wasteDatabase.put("laptop", new WasteInfo("Hazardous Electronic Waste", "💻",
                "💻 Laptops contain hazardous materials!\n\n✅ E-waste facility\n✅ Erase data first\n✅ Donate if working\n✅ Recycling program payment",
                "Laptop recycling recovers rare earth elements needed for new electronics."));

        wasteDatabase.put("computer", new WasteInfo("Hazardous Electronic Waste", "🖥️",
                "🖥️ Desktop computers are complex!\n\n✅ E-waste facility\n✅ Donation programs available\n✅ Manufacturer take-back\n✅ Data destruction service",
                "Computer recycling prevents arsenic and lead release into environment."));

        wasteDatabase.put("monitor", new WasteInfo("Hazardous Electronic Waste", "🖥️",
                "🖥️ CRT monitors contain lead!\n\n❌ NOT standard recycling\n✅ E-waste facility\n✅ Separate from LCD monitors\n✅ Free programs available",
                "CRT monitors must be handled separately due to lead content."));

        wasteDatabase.put("keyboard", new WasteInfo("Electronic Waste (Low Hazard)", "⌨️",
                "⌨️ Keyboards are mostly plastic!\n\n✅ E-waste facility\n✅ Some might be reusable\n✅ Parts recycling possible\n✅ Check donation first",
                "Keyboard electronics are valuable; donation extends product life."));

        wasteDatabase.put("mouse", new WasteInfo("Electronic Waste (Low Hazard)", "🖱️",
                "🖱️ Computer mice have circuits!\n\n✅ E-waste facility\n✅ Reusable if working\n✅ Parts valuable\n✅ Donate if functional",
                "Wireless mice contain batteries requiring special handling."));

        wasteDatabase.put("printer", new WasteInfo("Hazardous Electronic Waste", "🖨️",
                "🖨️ Printers are complex e-waste!\n\n✅ E-waste facility\n✅ Donation programs\n✅ Manufacturer programs\n✅ Check bulk pickup",
                "Printer recycling recovers plastic, metals, and circuit components."));

        wasteDatabase.put("scanner", new WasteInfo("Electronic Waste (Complex)", "📠",
                "📠 Scanners contain circuit boards!\n\n✅ E-waste facility\n✅ Donation if working\n✅ Parts salvage\n✅ Bulk e-waste program",
                "Scanners are often refurbished and sold; donate if functional."));

        wasteDatabase.put("router", new WasteInfo("Electronic Waste (Electronics)", "📡",
                "📡 WiFi routers need e-waste!\n\n✅ E-waste facility\n✅ Some reuse programs\n✅ Factory reset for donation\n✅ Contains valuable metals",
                "Routers contain circuit boards with copper and rare metals."));

        wasteDatabase.put("charger", new WasteInfo("Electronic Waste (Hazardous)", "🔌",
                "🔌 Power chargers are e-waste!\n\n❌ NOT trash or regular recycling\n✅ E-waste facility\n✅ Donation if working\n✅ Bulk e-waste pickup",
                "Chargers contain capacitors and circuit boards with hazardous materials."));

        wasteDatabase.put("headphones", new WasteInfo("Electronic Waste (Mixed)", "🎧",
                "🎧 Headphones contain metals!\n\n✅ E-waste facility\n✅ Reusable if working\n✅ Parts salvage\n✅ Donation programs",
                "Headphone recycling recovers copper and rare earth magnets."));

        wasteDatabase.put("tv", new WasteInfo("Hazardous Electronic Waste (Large)", "📺",
                "📺 Old TVs contain hazardous materials!\n\n❌ NOT curbside pickup\n✅ E-waste facility\n✅ Donation if working\n✅ Bulk pickup programs\n✅ Manufacturer take-back",
                "TV recycling prevents mercury from CRT tubes entering environment."));

        wasteDatabase.put("gaming console", new WasteInfo("Electronic Waste (Complex)", "🎮",
                "🎮 Gaming consoles are e-waste!\n\n✅ E-waste facility\n✅ Donation if working\n✅ Trade-in value\n✅ Repair programs",
                "Console recycling recovers circuit boards and valuable metals."));

        wasteDatabase.put("camera", new WasteInfo("Electronic Waste (Optics)", "📷",
                "📷 Cameras are electronic waste!\n\n✅ E-waste facility\n✅ Donation if working\n✅ Photography programs\n✅ Repair value",
                "Camera lenses and circuit boards contain valuable recyclable materials."));

        // ===================== TEXTILES (12) =====================
        wasteDatabase.put("cloth", new WasteInfo("Reusable / Recyclable Textile", "👕",
                "👕 Cloth is valuable!\n\n✅ Donate if wearable\n✅ Thrift stores\n✅ Textile recycling\n✅ Rag/industrial use",
                "Textile reuse extends product life 1-7 years before recycling."));

        wasteDatabase.put("clothes", new WasteInfo("Reusable / Recyclable Textile", "👔",
                "👔 Clothes should be reused first!\n\n✅ Donate to charity\n✅ Clothing swap events\n✅ Thrift stores\n✅ Textile recycler if worn",
                "Every piece of clothing donated prevents landfill waste."));

        wasteDatabase.put("shirt", new WasteInfo("Reusable / Recyclable Textile", "👕",
                "👕 Shirts are valuable donations!\n\n✅ Good condition: donate\n✅ Thrift stores\n✅ Charities\n✅ Textile recycler if torn",
                "Donated shirts are often sold in developing countries."));

        wasteDatabase.put("pants", new WasteInfo("Reusable / Recyclable Textile", "👖",
                "👖 Pants have long life span!\n\n✅ Donate wearable pairs\n✅ Thrift stores\n✅ Friends/family\n✅ Textile recycler if damaged",
                "High-quality pants are highly valued in secondhand market."));

        wasteDatabase.put("jacket", new WasteInfo("Valuable Reusable Textile", "🧥",
                "🧥 Jackets are highly desirable!\n\n✅ Donate if usable\n✅ Thrift stores pay well\n✅ Consignment shops\n✅ Textile recycler if beyond repair",
                "Jacket donation helps vulnerable populations access winter clothing."));

        wasteDatabase.put("shoes", new WasteInfo("Reusable / Recyclable Textile", "👟",
                "👟 Shoes should be reused!\n\n✅ Donate if wearable\n✅ Thrift stores\n✅ Shoe recycling programs\n✅ Sole recycling for playgrounds",
                "Shoe soles are recycled into playground surfaces and athletic mats."));

        wasteDatabase.put("socks", new WasteInfo("Recyclable Textile (Pairs Only)", "🧦",
                "🧦 Socks are recyclable!\n\n✅ Keep matched pairs\n✅ Donate if clean\n✅ Textile recycler\n✅ Industrial rags if worn",
                "Socks are made into cleaning cloths and insulation materials."));

        wasteDatabase.put("towel", new WasteInfo("Reusable / Recyclable Textile", "🧽",
                "🧽 Towels have long life!\n\n✅ Donate if usable\n✅ Thrift stores\n✅ Animal shelters\n✅ Textile recycler when worn",
                "Used towels are valuable to animal shelters and cleaning businesses."));

        wasteDatabase.put("curtain", new WasteInfo("Reusable / Recyclable Textile", "🪟",
                "🪟 Curtains should be reused!\n\n✅ Donate if good condition\n✅ Thrift stores\n✅ Theaters/schools\n✅ Textile recycler if worn",
                "Quality curtains are valued by thrift stores and theater programs."));

        wasteDatabase.put("blanket", new WasteInfo("Valuable Reusable Textile", "🛏️",
                "🛏️ Blankets are highly needed!\n\n✅ Donate to shelters\n✅ Homeless services\n✅ Thrift stores\n✅ Textile recycler if damaged",
                "Donated blankets provide warmth to vulnerable populations."));

        wasteDatabase.put("bedsheet", new WasteInfo("Reusable / Recyclable Textile", "🛏️",
                "🛏️ Bed sheets should be reused!\n\n✅ Donate if clean\n✅ Thrift stores\n✅ Shelters\n✅ Textile recycler for cutting",
                "Used sheets become cleaning cloths, industrial rags, and craft materials."));

        wasteDatabase.put("textile", new WasteInfo("Reusable / Recyclable Material", "🧵",
                "🧵 All textiles have value!\n\n✅ Donation first\n✅ Reuse centers\n✅ Textile recycler\n✅ Fiber recovery programs",
                "Textile recycling saves water and reduces manufacturing waste."));

        // ===================== HAZARDOUS (12) =====================
        wasteDatabase.put("paint can", new WasteInfo("Hazardous Waste (Special Handling)", "🎨",
                "🎨 Paint contains hazardous chemicals!\n\n❌ NOT trash or recycling\n✅ Hazmat facility\n✅ Paint disposal program\n✅ Check with municipality",
                "Paint chemicals contaminate soil and groundwater if improperly disposed."));

        wasteDatabase.put("oil", new WasteInfo("Hazardous Waste (Toxic)", "🛢️",
                "🛢️ Oil requires special disposal!\n\n❌ NEVER down drain\n✅ Hazmat facility\n✅ Auto shops collect\n✅ Oil change facilities",
                "Used oil contaminates water supplies; recycle at collection points."));

        wasteDatabase.put("chemical", new WasteInfo("Hazardous Waste (Toxic)", "⚗️",
                "⚗️ Chemicals are dangerous!\n\n❌ NEVER in trash\n✅ Hazmat facility\n✅ Poison control info on container\n✅ Keep sealed",
                "Chemical disposal prevents environmental contamination and injury."));

        wasteDatabase.put("pesticide", new WasteInfo("Hazardous Waste (Poisonous)", "☠️",
                "☠️ Pesticides are highly toxic!\n\n❌ NEVER in trash or recycling\n✅ Hazmat facility\n✅ Keep sealed\n✅ Report spills",
                "Pesticide contamination affects food supply and groundwater."));

        wasteDatabase.put("fluorescent light", new WasteInfo("Hazardous (Mercury Content)", "💡",
                "💡 Fluorescent bulbs contain mercury!\n\n❌ NOT regular trash\n✅ Hazmat facility\n✅ Electronics recycler\n✅ Check light recycling program",
                "Mercury in bulbs prevents accumulation in food chain."));

        wasteDatabase.put("thermometer", new WasteInfo("Hazardous (Mercury)", "🌡️",
                "🌡️ Old thermometers contain mercury!\n\n❌ NOT trash\n✅ Hazmat facility\n✅ EPA collection\n✅ Handle with care",
                "Mercury thermometers pose serious health and environmental risks."));

        wasteDatabase.put("asbestos", new WasteInfo("Extremely Hazardous Waste", "⚠️",
                "⚠️ Asbestos is carcinogenic!\n\n❌ NEVER touch\n✅ Licensed removal specialist\n✅ Regulated disposal\n✅ EPA certified facility",
                "Asbestos fibers cause mesothelioma and lung cancer; require professional removal."));

        wasteDatabase.put("lead paint", new WasteInfo("Hazardous (Lead)", "🎨",
                "🎨 Old paint may contain lead!\n\n⚠️ Pre-1978 homes: test first\n✅ Professional lead abatement\n✅ Hazmat facility\n✅ EPA certified",
                "Lead paint poses severe neurological risk to children."));

        wasteDatabase.put("medical waste", new WasteInfo("Biohazardous Waste", "💉",
                "💉 Medical waste needs special care!\n\n✅ Sharps containers\n✅ Medical facility disposal\n✅ Pharmacies (medications)\n✅ Hazmat facility",
                "Medical waste handling prevents infection and contamination risks."));

        wasteDatabase.put("propane tank", new WasteInfo("Hazardous (Pressurized Gas)", "🛢️",
                "🛢️ Propane tanks are pressurized!\n\n❌ NEVER in trash\n✅ Return to supplier\n✅ Exchange program\n✅ Hazmat facility if damaged",
                "Pressurized containers pose explosion risk if mishandled."));

        wasteDatabase.put("motor oil", new WasteInfo("Hazardous (Toxic/Flammable)", "🛢️",
                "🛢️ Motor oil is valuable and hazardous!\n\n✅ Auto parts stores collect\n✅ Oil change centers\n✅ Hazmat facility\n✅ Recycle for industrial use",
                "One quart of oil contaminates 250,000 gallons of water."));

        wasteDatabase.put("solvent", new WasteInfo("Hazardous (Volatile Chemical)", "⚗️",
                "⚗️ Solvents are flammable!\n\n❌ NOT trash or drain\n✅ Hazmat facility\n✅ Keep sealed and cool\n✅ Professional disposal",
                "Solvent disposal prevents groundwater contamination."));

        // ===================== CONSTRUCTION/MISC (12) =====================
        wasteDatabase.put("wood", new WasteInfo("Recyclable / Compostable (Untreated)", "🪵",
                "🪵 Untreated wood is recyclable!\n\n✅ Untreated: chip/mulch\n❌ Treated: hazmat facility\n✅ Check for stains/preservatives",
                "Untreated wood recycling prevents methane from landfills."));

        wasteDatabase.put("drywall", new WasteInfo("Recyclable Construction Material", "🏗️",
                "🏗️ Drywall is recyclable!\n\n✅ Clean drywall: recycler\n✅ Damaged: construction debris\n✅ Remove nails/fixtures\n✅ Bulk pickup available",
                "Drywall recycling becomes new wallboard, reducing landfill 30%."));

        wasteDatabase.put("concrete", new WasteInfo("Recyclable Construction Aggregate", "🏗️",
                "🏗️ Concrete is valuable!\n\n✅ Remove rebar\n✅ Crushing facility\n✅ Becomes gravel/aggregate\n✅ Construction recycling center",
                "Recycled concrete is crushed for road base and new construction."));

        wasteDatabase.put("brick", new WasteInfo("Reusable Construction Material", "🧱",
                "🧱 Bricks are reusable!\n\n✅ Salvage for reuse\n✅ High salvage value\n✅ Architectural reclamation\n✅ Landscaping material",
                "Historic bricks command premium prices for restoration projects."));

        wasteDatabase.put("metal roofing", new WasteInfo("Recyclable Metal (High Value)", "🏠",
                "🏠 Metal roofing is valuable!\n\n✅ Scrap metal facility\n✅ High cash value\n✅ Separate by metal type\n✅ Remove fasteners",
                "Metal roofing recycling prevents material loss and landfill use."));

        wasteDatabase.put("shingles", new WasteInfo("Recyclable (Asphalt Shingles)", "🏠",
                "🏠 Asphalt shingles are recyclable!\n\n✅ Specialty recycler\n✅ Becomes asphalt\n✅ Road aggregate\n✅ Check local programs",
                "Shingle recycling diverts 400,000 tons annually from landfills."));

        wasteDatabase.put("lumber", new WasteInfo("Recyclable / Reusable (Untreated)", "🪵",
                "🪵 Lumber has many uses!\n\n✅ Untreated: reuse/recycle\n✅ Treated: hazmat facility\n✅ Salvage yards\n✅ Woodworking projects",
                "Reclaimed lumber is prized for character and sustainability."));

        wasteDatabase.put("tile", new WasteInfo("Recyclable / Reusable", "🟦",
                "🟦 Ceramic tiles are durable!\n\n✅ Reuse in renovations\n✅ Salvage yards\n✅ Ceramic recycler\n✅ Crushed for aggregate",
                "Tile recycling extends material life indefinitely."));

        wasteDatabase.put("insulation", new WasteInfo("Recyclable (Fiberglass/Mineral)", "🎛️",
                "🎛️ Insulation is recyclable!\n\n✅ Fiberglass: recycler\n✅ Mineral wool: recycler\n✅ Wear mask when handling\n✅ Construction debris program",
                "Insulation recycling recovers valuable materials."));

        wasteDatabase.put("plumbing pipe", new WasteInfo("Recyclable Metal (PVC/Metal)", "💧",
                "💧 Plumbing pipes are recyclable!\n\n✅ Metal: scrap yard\n✅ PVC: specialty recycler\n✅ Separate by material\n✅ Remove fittings",
                "Pipe recycling prevents landfill accumulation of durable materials."));

        wasteDatabase.put("fixture", new WasteInfo("Recyclable / Reusable (Metal/Ceramic)", "🚿",
                "🚿 Bathroom fixtures are reusable!\n\n✅ Donate if working\n✅ Salvage yards\n✅ Metal/ceramic recycler\n✅ High resale value",
                "Fixture reuse extends product life 20+ years."));

        wasteDatabase.put("cabinet", new WasteInfo("Reusable / Recyclable (Wood/Metal)", "🗄️",
                "🗄️ Cabinets are valuable!\n\n✅ Donate if usable\n✅ Restoration projects\n✅ Thrift stores\n✅ Wood recycler if damaged",
                "Cabinet reuse prevents manufacturing waste and saves money."));

        // ===================== MISCELLANEOUS (15) =====================
        wasteDatabase.put("rubber", new WasteInfo("Recyclable / Reusable Material", "🛞",
                "🛞 Rubber has many applications!\n\n✅ Tire recycler\n✅ Playgrounds (mulch)\n✅ Asphalt (crumb)\n✅ Athletic tracks",
                "Tire recycling creates playground surfaces protecting 100,000+ kids."));

        wasteDatabase.put("foam", new WasteInfo("Specialty Recyclable (Check Type)", "🟦",
                "🟦 Foam recycling varies!\n\n⚠️ Polystyrene: specialty facility\n✅ Polyurethane: recycler\n✅ Check local programs\n✅ Not curbside pickup",
                "Foam recycling diverts 125,000 tons from landfills annually."));

        wasteDatabase.put("mattress", new WasteInfo("Recyclable / Reusable (Bulk Item)", "🛏️",
                "🛏️ Mattresses are recyclable!\n\n✅ Donate if usable\n✅ Mattress recycling program\n✅ Disassemble for material recovery\n✅ Bulk pickup",
                "Mattress recycling recovers foam, wood, metal, and fabric."));

        wasteDatabase.put("furniture", new WasteInfo("Reusable / Recyclable (Mixed)", "🪑",
                "🪑 Furniture should be reused!\n\n✅ Donate if usable\n✅ Thrift stores\n✅ Salvage yards\n✅ Disassembly for material recycling",
                "Furniture reuse extends product life and supports communities."));

        wasteDatabase.put("leather", new WasteInfo("Reusable / Specialized Recycling", "👜",
                "👜 Leather items are valuable!\n\n✅ Donate if wearable\n✅ Specialty recycler\n✅ Thrift stores\n✅ Leather crafts projects",
                "Leather reuse prevents animal exploitation and waste."));

        wasteDatabase.put("cork", new WasteInfo("Recyclable / Compostable", "🍷",
                "🍷 Cork is natural and recyclable!\n\n✅ Cork recycler\n✅ Craft projects\n✅ Mulch\n✅ Insulation material",
                "Cork recycling creates products without virgin extraction."));

        wasteDatabase.put("ceramic", new WasteInfo("Recyclable / Reusable", "🍽️",
                "🍽️ Ceramic is valuable!\n\n✅ Donate if usable\n✅ Thrift stores\n✅ Ceramic recycler\n✅ Crushed for aggregate",
                "Ceramic reuse extends product life indefinitely."));

        wasteDatabase.put("rubber glove", new WasteInfo("Non-Recyclable / Hazardous", "🧤",
                "🧤 Used gloves need care!\n\n❌ NOT recyclable if contaminated\n✅ Medical waste if biohazard\n✅ Trash otherwise",
                "Contaminated gloves are classified as hazardous waste."));

        wasteDatabase.put("bulk item", new WasteInfo("Mixed Materials (Check Type)", "📦",
                "📦 Large items need special handling!\n\n✅ Bulk trash pickup\n✅ Donation if usable\n✅ Disassemble for materials\n✅ Scrap facility",
                "Bulk item recycling requires material separation."));

        wasteDatabase.put("mixed waste", new WasteInfo("Contaminated Material", "❓",
                "❓ Mixed contaminated waste!\n\n✅ Identify materials\n✅ Separate by type\n✅ Hazmat if needed\n✅ Then recycle appropriately",
                "Contamination prevents effective recycling; separate materials first."));

        wasteDatabase.put("unknown item", new WasteInfo("Uncertain Classification", "❓",
                "❓ Unsure about this item?\n\n✅ Research material type\n✅ Check recycling symbol\n✅ Contact local facility\n✅ Better to err on caution side",
                "When in doubt, contact your local waste management facility."));

        wasteDatabase.put("synthetic fabric", new WasteInfo("Specialized Textile Recycling", "🧵",
                "🧵 Synthetic fabrics are recyclable!\n\n✅ Polyester: textile recycler\n✅ Nylon: textile recycler\n✅ Donate if wearable\n✅ Industrial use if worn",
                "Synthetic fabric recycling reduces petroleum dependence."));

        wasteDatabase.put("natural fiber", new WasteInfo("Compostable / Recyclable", "🌿",
                "🌿 Natural fibers are valuable!\n\n✅ Cotton: textile recycler\n✅ Wool: textile recycler\n✅ Jute: compostable\n✅ Donate if wearable",
                "Natural fiber recycling reduces agricultural waste."));

        wasteDatabase.put("composite material", new WasteInfo("Specialty Recycling", "🟧",
                "🟧 Composites are challenging!\n\n⚠️ Check material composition\n✅ Some specialty recyclers\n⚠️ Difficult to separate\n✅ Check local programs",
                "Composite recycling is emerging technology; check availability."));
    }

    private void initializeViews() {
        textViewItemName = findViewById(R.id.textViewItemName);
        textViewCategory = findViewById(R.id.textViewCategory);
        textViewInstructions = findViewById(R.id.textViewInstructions);
        textViewImpact = findViewById(R.id.textViewImpact);
        buttonBack = findViewById(R.id.buttonBack);
        buttonSortAnother = findViewById(R.id.buttonSortAnother);

        buttonGoHome = new Button(this);
        buttonGoHome.setText("🏠 Go to Home");
        buttonGoHome.setTextSize(15f);
        buttonGoHome.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        buttonGoHome.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary_green));
        buttonGoHome.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);
        buttonGoHome.setLayoutParams(params);

        android.widget.LinearLayout parent = (android.widget.LinearLayout) buttonSortAnother.getParent();
        parent.addView(buttonGoHome);
    }

    private void displayResult(String itemName) {
        String[] words = itemName.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        textViewItemName.setText(capitalized.toString().trim());

        String lowerItem = itemName.toLowerCase();
        WasteInfo info = wasteDatabase.get(lowerItem);

        if (info != null) {
            textViewCategory.setText(info.category + " " + info.emoji);
            textViewInstructions.setText(info.instructions);
            textViewImpact.setText(info.impact);
        } else {
            textViewCategory.setText("General Waste ♻️");
            textViewInstructions.setText("♻️ When in doubt:\n\n✅ Look for recycling symbols\n✅ Contact local waste management\n❌ When unsure, place in general waste");
            textViewImpact.setText("Correct waste sorting helps Tunisia build a sustainable future!");
        }
    }

    private void setupButtonListeners() {
        buttonBack.setOnClickListener(v -> finish());

        buttonSortAnother.setOnClickListener(v -> {
            startActivity(new Intent(SortResultActivity.this, WhatToSortActivity.class));
            finish();
        });

        buttonGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(SortResultActivity.this, MainDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private class WasteInfo {
        String category, emoji, instructions, impact;

        WasteInfo(String category, String emoji, String instructions, String impact) {
            this.category = category;
            this.emoji = emoji;
            this.instructions = instructions;
            this.impact = impact;
        }
    }
}