import '../App.css'
import "bootstrap-icons/font/bootstrap-icons.css";

export default function Footer() {
    return (
        <div className="Footer Wrap bg-secondary">
            <div className="container">
                <p className="text-white">© {new Date().getFullYear()} Njord OpenENC Authors</p>
                {/*<p className="text-white bi-github"> Github</p>*/}
            </div>
        </div>
    )
}